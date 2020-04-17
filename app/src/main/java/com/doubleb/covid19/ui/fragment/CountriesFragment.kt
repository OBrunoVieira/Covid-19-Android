package com.doubleb.covid19.ui.fragment

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
import com.doubleb.covid19.ui.activity.CountryActivity
import com.doubleb.covid19.ui.adapter.CountriesAdapter
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.view_model.CountriesViewModel
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_countries.*
import org.koin.android.ext.android.inject

class CountriesFragment : Fragment(), ClickListener<Country?> {


    private val viewModel: CountriesViewModel by inject()

    private var adapter = CountriesAdapter(listener = this)
    private var placeholderList = arrayListOf(Country(), Country(), Country())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_countries, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.COUNTRIES)
        countries_recycler_view.adapter = adapter

        viewModel.liveData.observe(viewLifecycleOwner, observeCasesByCountries())
        viewModel.getCasesByCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearViewModel()
    }

    private fun observeCasesByCountries() =
        Observer<DataSource<List<Country>>> {
            when (it.dataState) {
                DataState.LOADING -> {
                    countries_error_view.visibility = GONE
                    countries_recycler_view.visibility = VISIBLE

                    adapter.list.clear()
                    adapter.list.addAll(placeholderList)
                    adapter.notifyDataSetChanged()
                }

                DataState.SUCCESS -> {
                    it.data?.let { worldDataList ->
                        adapter.list.clear()
                        adapter.list.addAll(worldDataList)
                        adapter.notifyItemRangeChanged(0, adapter.list.size - 1)
                    }
                }

                DataState.ERROR -> {
                    countries_recycler_view.visibility = GONE
                    countries_error_view
                        .throwable(it.throwable)
                        .reload(View.OnClickListener {
                            viewModel.getCasesByCountries()
                        })
                        .build()
                        .visibility = VISIBLE
                }
            }
        }

    override fun onItemClicked(data: Country?, position: Int) {
        data?.let {
            startActivity(
                Intent(activity, CountryActivity::class.java)
                    .putExtra(CountryActivity.ARGUMENTS_COUNTRY_NAME, it.name)
            )
        }
    }
}