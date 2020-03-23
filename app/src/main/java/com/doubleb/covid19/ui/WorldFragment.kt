package com.doubleb.covid19.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.model.WorldData
import com.doubleb.covid19.ui.adapter.WorldAdapter
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.WorldViewModel
import kotlinx.android.synthetic.main.fragment_world.*
import org.koin.android.ext.android.inject

class WorldFragment : Fragment(), ClickListener<Country?> {

    companion object{
        const val TAG = "WorldFragment"
    }

    private val viewModel: WorldViewModel by inject()

    private var placeholderList = arrayListOf(WorldData(), WorldData(), WorldData(), WorldData())
    private var adapter = WorldAdapter(listener = this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_world, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveDataWorldCases.observe(viewLifecycleOwner, observeWorldCases())
        viewModel.liveDataCasesByCountry.observe(viewLifecycleOwner, observeCasesByCountries())
        viewModel.getWorldCases()
        viewModel.getCasesByCountries()

        world_recycler_view.adapter = adapter
    }

    private fun observeWorldCases() =
        Observer<DataSource<WorldData>> {
            when (it.dataState) {
                DataState.LOADING -> {
                    adapter.list.clear()
                    adapter.list.addAll(placeholderList)
                    adapter.notifyDataSetChanged()
                }

                DataState.SUCCESS -> {
                    it.data?.let { worldData ->
                        adapter.list[1] = worldData
                        adapter.notifyItemChanged(1)
                    }
                }

                DataState.ERROR -> {

                }
            }
        }

    private fun observeCasesByCountries() =
        Observer<DataSource<List<WorldData>>> {
            when (it.dataState) {
                DataState.LOADING -> {
                    adapter.list.clear()
                    adapter.list.addAll(placeholderList)
                    adapter.notifyDataSetChanged()
                }

                DataState.SUCCESS -> {
                    it.data?.let { worldDataList ->
                        adapter.list.run {
                            removeAt(size - 1)
                            removeAt(size - 1)

                            addAll(worldDataList)
                            adapter.notifyItemRangeChanged(2, size - 1)
                        }
                    }
                }

                DataState.ERROR -> {

                }
            }
        }

    override fun onItemClicked(data: Country?, position: Int) {
        startActivity(
            Intent(activity, CountryActivity::class.java)
                .putExtra(CountryActivity.ARGUMENTS_COUNTRY_NAME, data?.country)
        )
    }

}