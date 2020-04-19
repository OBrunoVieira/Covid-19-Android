package com.doubleb.covid19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.gone
import com.doubleb.covid19.extensions.visible
import com.doubleb.covid19.model.News
import com.doubleb.covid19.model.NewsResult
import com.doubleb.covid19.ui.adapter.NewsAdapter
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.NewsViewModel
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject

class NewsFragment : Fragment() {
    companion object {
        const val TAG = "NewsFragment"
    }

    private val viewModel: NewsViewModel by inject()

    private val adapter = NewsAdapter()

    private val placeholderList = arrayListOf(News(), News())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.NEWS)
        news_recycler_view.adapter = adapter

        viewModel.liveData.observe(viewLifecycleOwner, observeTopHeadlines())
        viewModel.getTopHeadlines()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearViewModel()
    }

    private fun observeTopHeadlines() =
        Observer<DataSource<NewsResult>> {
            when (it.dataState) {
                DataState.LOADING -> {
                    news_error_view.gone()
                    news_recycler_view.visible()

                    adapter.list.clear()
                    adapter.list.addAll(placeholderList)
                    adapter.notifyDataSetChanged()
                }

                DataState.SUCCESS -> {
                    it.data?.articles?.let { newsList ->
                        adapter.list.clear()
                        adapter.list.addAll(newsList)
                        adapter.notifyItemRangeChanged(0, adapter.list.size - 1)
                    }
                }

                DataState.ERROR -> {
                    news_recycler_view.gone()
                    news_error_view
                        .throwable(it.throwable)
                        .reload(View.OnClickListener {
                            viewModel.getTopHeadlines()
                        })
                        .show()
                }
            }
        }
}