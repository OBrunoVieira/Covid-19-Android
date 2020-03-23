package com.doubleb.covid19.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.adapter.SearchAdapter
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject
import java.util.*

class SearchActivity :
    AppCompatActivity(R.layout.activity_search),
    ClickListener<Country> {

    companion object {
        const val HOME_ORIGIN = 1100
        const val WORLD_ORIGIN = 1111

        const val RESULT_DATA_COUNTRY_NAME = "RESULT_DATA_COUNTRY_NAME"
        const val ARGUMENTS_ORIGIN = "ARGUMENTS_ORIGIN"
    }

    private val viewModel: SearchViewModel by inject()
    private val searchAdapter = SearchAdapter(listener = this)

    private var origin = WORLD_ORIGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        origin = intent.getIntExtra(ARGUMENTS_ORIGIN, WORLD_ORIGIN)

        viewModel.liveData.observe(this, observeCountryNames())
        viewModel.getCountryNames()

        search_image_view_arrow.setOnClickListener { onBackPressed() }
        search_edit_text.addTextChangedListener(onTextChanged = { text: CharSequence?, _: Int, count: Int, _: Int ->
            filterTypedText(count, text.toString())
        })

        search_recycler_view.run {
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    private fun filterTypedText(count: Int, text: String) {
        if (text.count() > 0) {
            val filteredList =
                viewModel.list?.filter {
                    val country = it.country?.toLowerCase(Locale.getDefault())
                    val currentText = text.toLowerCase(Locale.getDefault())
                    country?.contains(currentText) == true
                }

            buildSearchList(filteredList)
        } else {
            buildSearchList(viewModel.list)
        }
    }

    private fun buildSearchList(newList: List<Country>?) {
        searchAdapter.list.clear()
        if (!newList.isNullOrEmpty()) {
            searchAdapter.list.addAll(newList)
            search_text_view_not_found.visibility = GONE
            search_card_view.visibility = VISIBLE
        } else {
            search_text_view_not_found.visibility = VISIBLE
            search_card_view.visibility = GONE
        }

        searchAdapter.notifyDataSetChanged()
    }

    private fun observeCountryNames() = Observer<DataSource<List<Country>>> {
        when (it.dataState) {
            DataState.LOADING -> {
                search_progress.show()
                search_error_view.visibility = GONE
                search_card_view.visibility = GONE

                search_edit_text.isEnabled = false
                search_edit_text.alpha = .5f
            }

            DataState.SUCCESS -> {
                search_progress.hide()
                search_error_view.visibility = GONE
                search_card_view.visibility = VISIBLE

                search_edit_text.isEnabled = true
                search_edit_text.alpha = 1f
                buildSearchList(it.data)
            }

            DataState.ERROR -> {
                search_progress.hide()
                search_edit_text.isEnabled = false
                search_edit_text.alpha = .5f
                search_card_view.visibility = GONE

                search_error_view
                    .errorType(it.throwable)
                    .reload(View.OnClickListener {
                        viewModel.getCountryNames()
                    })
                    .build()
                    .visibility = VISIBLE
            }
        }
    }

    override fun onItemClicked(data: Country?, position: Int) {
        search_edit_text.setText(data?.country)
        buildSearchList(viewModel.list)

        if (origin == HOME_ORIGIN) {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra(RESULT_DATA_COUNTRY_NAME, data?.country)
            )
            onBackPressed()
            return
        }

        startActivity(
            Intent(this, CountryActivity::class.java)
                .putExtra(CountryActivity.ARGUMENTS_COUNTRY_NAME, data?.country)
        )
    }
}