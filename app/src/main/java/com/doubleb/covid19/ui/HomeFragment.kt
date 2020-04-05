package com.doubleb.covid19.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
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
    }

    private val viewModel: HomeViewModel by inject()

    private lateinit var countryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.HOME)
        countryName = requireActivity().getString(R.string.country)

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
                getCountry(data?.getStringExtra(SearchActivity.RESULT_DATA_COUNTRY_NAME))

            if (resultName != countryName) {
                countryName = resultName
                viewModel.getByCountry(countryName)
            }
        }
    }

    private fun clickToSearch() {
        startActivityForResult(
            Intent(activity, SearchActivity::class.java)
                .putExtra(SearchActivity.ARGUMENTS_ORIGIN, SearchActivity.HOME_ORIGIN),
            SEARCH_RESULT
        )
    }

    private fun observeCountry() = Observer<DataSource<Country>> {
        when (it.dataState) {
            DataState.LOADING -> {
                home_error_view.visibility = View.GONE
                home_content_data.visibility = View.VISIBLE

                home_chart_card_view.loading()
                home_today_cases_view.loading()
                home_text_view_sub_title.loading()
            }

            DataState.SUCCESS -> {
                it.data?.let { result ->
                    home_error_view.visibility = View.GONE
                    home_content_data.visibility = View.VISIBLE

                    home_chart_card_view
                        .totalCases(result.cases)
                        .activeCases(result.active)
                        .recoveredCases(result.recovered)
                        .deathCases(result.deaths)
                        .build()

                    home_today_cases_view
                        .todayCases(result.todayCases)
                        .todayDeaths(result.todayDeaths)
                        .build()

                    home_text_view_sub_title.setLoadedText(getCountry(result.country))
                }
            }

            DataState.ERROR -> {
                home_content_data.visibility = View.GONE

                home_error_view
                    .errorType(it.throwable)
                    .reload(View.OnClickListener {
                        viewModel.getByCountry(countryName)
                    })
                    .build()
                    .visibility = View.VISIBLE
            }
        }
    }

    private fun getCountry(country: String? = null) =
        if (country.isNullOrEmpty() && countryName.isNotEmpty()) {
            countryName
        } else if (country.isNullOrEmpty()) {
            getString(R.string.country)
        } else {
            country
        }
}