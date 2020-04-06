package com.doubleb.covid19.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.BaseData
import com.doubleb.covid19.storage.Preferences
import com.doubleb.covid19.ui.activity.SearchActivity
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.HomeViewModel
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
        private const val SEARCH_RESULT = 1122
        private const val COUNTRY_NAME_KEY = "COUNTRY_NAME_KEY"
    }

    private val viewModel: HomeViewModel by inject()

    private val countryName by lazy {
        Preferences.readStringValue(requireContext(),
            COUNTRY_NAME_KEY
        ) ?: run {
            val defaultValue = getString(R.string.country)
            Preferences.writeValue(requireContext(),
                COUNTRY_NAME_KEY, defaultValue)
            return@run defaultValue
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.HOME)

        home_text_view_sub_title.setOnClickListener { clickToSearch() }
        viewModel.liveData.observe(viewLifecycleOwner, observeCountry())

        viewModel.getByCountry(countryName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_RESULT && resultCode == Activity.RESULT_OK) {
            val resultName =
                data?.getStringExtra(SearchActivity.RESULT_DATA_COUNTRY_NAME) ?: countryName

            if (resultName != countryName) {
                Preferences.writeValue(requireContext(),
                    COUNTRY_NAME_KEY, resultName)
                viewModel.getByCountry(resultName)
            }
        }
    }

    private fun clickToSearch() {
        startActivityForResult(
            Intent(activity, SearchActivity::class.java)
                .putExtra(
                    SearchActivity.ARGUMENTS_ORIGIN,
                    SearchActivity.HOME_ORIGIN
                ),
            SEARCH_RESULT
        )
    }

    private fun observeCountry() = Observer<DataSource<List<BaseData>>> {
        when (it.dataState) {
            DataState.LOADING -> {
                home_error_view.visibility = GONE
                home_content_data.visibility = View.VISIBLE

                home_chart_card_view.loading()
                home_spread_chart_card_view.loading()
                home_today_cases_view.loading()
                home_text_view_sub_title.loading()
            }

            DataState.SUCCESS -> {
                it.data?.let { result ->
                    val countryData = result[0].country
                    val historicalData = result[1].historical

                    home_error_view.visibility = GONE
                    home_content_data.visibility = View.VISIBLE

                    countryData?.let { data ->
                        home_chart_card_view
                            .totalCases(data.cases)
                            .activeCases(data.active)
                            .recoveredCases(data.recovered)
                            .deathCases(data.deaths)
                            .build()

                        home_today_cases_view
                            .todayCases(data.todayCases)
                            .todayDeaths(data.todayDeaths)
                            .build()

                        home_text_view_sub_title.setLoadedText(data.country)
                    }

                    historicalData?.let { historical ->
                        home_spread_chart_card_view
                            .loadChart(historical.recovered, historical.deaths, historical.cases)
                    }

                }
            }

            DataState.ERROR -> {
                home_content_data.visibility = GONE

                home_error_view
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