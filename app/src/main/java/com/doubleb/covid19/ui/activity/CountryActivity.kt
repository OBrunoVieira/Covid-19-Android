package com.doubleb.covid19.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.CountryData
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

    private fun observeCountry() = Observer<DataSource<CountryData>> {
        when (it.dataState) {
            DataState.LOADING -> {
                country_error_view.visibility = View.GONE
                country_content_data.visibility = View.VISIBLE

                country_chart_card_view.loading()
                country_today_cases_view.loading()
                country_spread_chart_card_view.loading()
            }

            DataState.SUCCESS -> {
                it.data?.country?.let { countryData ->
                    country_chart_card_view
                        .totalCases(countryData.cases)
                        .activeCases(countryData.active)
                        .recoveredCases(countryData.recovered)
                        .deathCases(countryData.deaths)
                        .build()

                    country_today_cases_view
                        .todayCases(countryData.todayCases)
                        .todayDeaths(countryData.todayDeaths)
                        .build()
                } ?: run {
                    country_chart_card_view.loading()
                    country_today_cases_view.loading()
                }

                it.data?.historical?.let { historicalResult ->
                    country_spread_chart_card_view
                        .loadChart(
                            historicalResult.recovered,
                            historicalResult.deaths,
                            historicalResult.cases
                        )
                } ?: run {
                    country_spread_chart_card_view.loading()
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