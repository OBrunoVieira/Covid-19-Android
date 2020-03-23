package com.doubleb.covid19.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.SearchActivity.Companion.HOME_ORIGIN
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_home_today_cases.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
        const val SEARCH_RESULT = 1122
        private const val ARGUMENTS_COUNTRY = "ARGUMENTS_COUNTRY"
        private const val ARGUMENTS_HAS_TOOLBAR = "ARGUMENTS_HAS_TOOLBAR"
        var countryName: String = ""

        fun newInstance(countryName: String = "", hasToolbar: Boolean = false) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENTS_COUNTRY, countryName)
                    putBoolean(ARGUMENTS_HAS_TOOLBAR, hasToolbar)
                }
            }
    }

    private val viewModel: HomeViewModel by inject()
    private var hasToolbar = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryName =
            getCountry(arguments?.getString(ARGUMENTS_COUNTRY))

        hasToolbar =
            arguments?.getBoolean(ARGUMENTS_HAS_TOOLBAR) == true

        home_text_view_sub_title.setOnClickListener {
            startActivityForResult(
                Intent(activity, SearchActivity::class.java)
                    .putExtra(SearchActivity.ARGUMENTS_ORIGIN, HOME_ORIGIN),
                SEARCH_RESULT
            )
        }

        if (hasToolbar) {
            home_text_view_title.visibility = GONE
            home_text_view_sub_title.visibility = GONE
        } else {
            home_text_view_title.visibility = VISIBLE
            home_text_view_sub_title.visibility = VISIBLE
        }

        viewModel.liveData.observe(viewLifecycleOwner, observeCountry())
        viewModel.getByCountry(countryName)
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

    private fun observeCountry() = Observer<DataSource<Country>> {
        when (it.dataState) {
            DataState.LOADING -> {
                home_chart_card_view.loading()
                home_text_view_new_cases_results.loading()
                home_text_view_new_deaths_results.loading()
                home_text_view_sub_title.loading()
            }

            DataState.SUCCESS -> {
                it.data?.let { result ->
                    home_chart_card_view
                        .totalCases(result.cases)
                        .activeCases(result.active)
                        .recoveredCases(result.recovered)
                        .deathCases(result.deaths)
                        .build()

                    home_text_view_sub_title.setLoadedText(getCountry(result.country))
                    home_text_view_date.text = "Formatar aqui"
                    home_text_view_new_cases_results.setLoadedText(result.todayCases.toString())
                    home_text_view_new_deaths_results.setLoadedText(result.todayDeaths.toString())
                }
            }

            DataState.ERROR -> {

            }
        }
    }

    private fun getCountry(country: String?) =
        if (country.isNullOrEmpty() && countryName.isNotEmpty()) {
            countryName
        } else if (country.isNullOrEmpty()) {
            getString(R.string.country)
        } else {
            country
        }
}