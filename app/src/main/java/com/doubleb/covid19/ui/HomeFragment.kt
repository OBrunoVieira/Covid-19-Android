package com.doubleb.covid19.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Result
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_home_today_cases.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, observeCountry())
        viewModel.getByCountry()
    }

    private fun observeCountry() = Observer<DataSource<Result>> {
        when (it.dataState) {
            DataState.LOADING -> {
                home_chart_card_view.loading()
            }

            DataState.SUCCESS -> {
                it.data?.let { result ->
                    home_chart_card_view
                        .totalCases(result.cases)
                        .activeCases(result.active)
                        .recoveredCases(result.recovered)
                        .criticalCases(result.critical)
                        .build()

                    home_text_view_date.text = "Formatar aqui"
                    home_text_view_new_cases_results.text = result.todayCases.toString()
                    home_text_view_new_deaths_results.text = result.todayDeaths.toString()
                }
            }

            DataState.ERROR -> {
//                home_text_view.text = "erro"
            }
        }
    }
}