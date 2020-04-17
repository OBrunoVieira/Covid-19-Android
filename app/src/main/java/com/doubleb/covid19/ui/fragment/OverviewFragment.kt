package com.doubleb.covid19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.WorldData
import com.doubleb.covid19.view_model.OverviewViewModel
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_overview.*
import org.koin.android.ext.android.inject

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.OVERVIEW)
        viewModel.liveDataWorldCases.observe(viewLifecycleOwner, observeWorldCases())
        viewModel.getWorldCases()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearViewModel()
    }

    private fun observeWorldCases() =
        Observer<DataSource<WorldData>> {
            when (it.dataState) {
                DataState.LOADING -> {
                    overview_chart_card_view.loading()
                    overview_spread_chart_card_view.loading()
                    overview_today_cases_view.loading()

                    overview_error_view.visibility = GONE
                    overview_group.visibility = VISIBLE
                }

                DataState.SUCCESS -> {
                    it.data?.country?.let { country ->
                        overview_chart_card_view
                            .totalCases(country.cases)
                            .activeCases(country.cases - country.recovered - country.deaths)
                            .deathCases(country.deaths)
                            .recoveredCases(country.recovered)
                            .build()

                        overview_today_cases_view
                            .todayCases(country.todayCases)
                            .todayDeaths(country.todayDeaths)
                            .build()
                    } ?: run {
                        overview_chart_card_view.loading()
                        overview_today_cases_view.loading()
                    }

                    it.data?.historical?.let { historicalResult ->
                        overview_spread_chart_card_view
                            .loadChart(
                                recovered = historicalResult.recovered,
                                deaths = historicalResult.deaths,
                                actives = historicalResult.cases
                            )
                    } ?: run {
                        overview_spread_chart_card_view.loading()
                    }
                }

                DataState.ERROR -> {
                    overview_group.visibility = GONE
                    overview_error_view
                        .throwable(it.throwable)
                        .reload(View.OnClickListener {
                            viewModel.getWorldCases()
                        })
                        .build()
                        .visibility = VISIBLE
                }
            }
        }
}