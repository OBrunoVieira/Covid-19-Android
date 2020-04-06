package com.doubleb.covid19.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.BaseData
import com.doubleb.covid19.view_model.CountryViewModel
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.activity_country.*
import org.koin.android.ext.android.inject

class CountryActivity : BaseActivity(R.layout.activity_country) {

    companion object {
        const val ARGUMENTS_COUNTRY_NAME = "ARGUMENTS_COUNTRY_NAME"
    }

    private val viewModel: CountryViewModel by inject()

    private lateinit var countryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.COUNTRY_DETAILS)
        countryName = intent.getStringExtra(ARGUMENTS_COUNTRY_NAME) ?: getString(R.string.country)

        country_image_view_back.setOnClickListener { onBackPressed() }
        country_text_view_title.text = countryName

        viewModel.liveData.observe(this, observeCountry())
        viewModel.getByCountry(countryName)
    }

    private fun observeCountry() = Observer<DataSource<List<BaseData>>> {
        when (it.dataState) {
            DataState.LOADING -> {
                country_error_view.visibility = View.GONE
                country_content_data.visibility = View.VISIBLE

                country_chart_card_view.loading()
                country_today_cases_view.loading()
                country_spread_chart_card_view.loading()
            }

            DataState.SUCCESS -> {
                it.data?.let { result ->
                    val countryData = result[0].country
                    val historicalData = result[1].historical

                    country_error_view.visibility = View.GONE
                    country_content_data.visibility = View.VISIBLE

                    countryData?.let { data ->
                        country_chart_card_view
                            .totalCases(data.cases)
                            .activeCases(data.active)
                            .recoveredCases(data.recovered)
                            .deathCases(data.deaths)
                            .build()

                        country_today_cases_view
                            .todayCases(data.todayCases)
                            .todayDeaths(data.todayDeaths)
                            .build()
                    }

                    historicalData?.let { historical ->
                        country_spread_chart_card_view
                            .loadChart(historical.recovered, historical.deaths, historical.cases)
                    }
                }
            }

            DataState.ERROR -> {
                country_content_data.visibility = View.GONE

                country_error_view
                    .throwable(it.throwable)
                    .reload(View.OnClickListener {
                        viewModel.getByCountry(countryName)
                    })
                    .build()
                    .visibility = View.VISIBLE
            }
        }
    }
}