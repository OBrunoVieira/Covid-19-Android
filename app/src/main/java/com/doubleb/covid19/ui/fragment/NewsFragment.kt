package com.doubleb.covid19.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.gone
import com.doubleb.covid19.extensions.visible
import com.doubleb.covid19.model.News
import com.doubleb.covid19.model.NewsResult
import com.doubleb.covid19.ui.adapter.NewsAdapter
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import com.doubleb.covid19.view_model.NewsViewModel
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject

class NewsFragment : Fragment(), ClickListener<News?> {

    private val viewModel: NewsViewModel by inject()

    private val adapter = NewsAdapter(listener = this)

    private val customTabsIntent by lazy {
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .addDefaultShareMenuItem()
            .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
            .build()
    }

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
        viewModel.liveDataState.observe(viewLifecycleOwner, observeState())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearViewModel()
    }

    override fun onItemClicked(data: News?, position: Int) {
        if (!data?.url.isNullOrEmpty()) {
            customTabsIntent.launchUrl(requireContext(), Uri.parse(data?.url))
        }
    }

    private fun observeState() = Observer<DataSource<NewsResult>> {
        when (it.dataState) {
            DataState.LOADING -> {
                if (adapter.itemCount == 0) {
                    news_content_loading.visible()
                    news_recycler_view.gone()
                } else {
                    news_content_loading.gone()
                    news_recycler_view.visible()
                }

                news_error_view.gone()

                adapter.state = it.dataState
            }

            DataState.SUCCESS -> {
                news_content_loading.gone()

                if(it.data?.articles.isNullOrEmpty()){
                    news_recycler_view.gone()
                    news_text_view_not_found.visible()
                } else {
                    news_text_view_not_found.gone()
                    news_recycler_view.visible()
                }

                adapter.state = it.dataState
            }

            DataState.ERROR -> {
                news_content_loading.gone()
                news_recycler_view.gone()
                news_error_view
                    .throwable(it.throwable)
                    .reload(View.OnClickListener {
                        viewModel.retry()
                    })
                    .show()
            }
        }
    }

    private fun observeTopHeadlines() =
        Observer<PagedList<News>> { adapter.submitList(it) }
}