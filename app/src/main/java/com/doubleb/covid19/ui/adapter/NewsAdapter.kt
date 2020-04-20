package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.doubleb.covid19.R
import com.doubleb.covid19.model.News
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.ui.view_holder.NewsViewHolder
import com.doubleb.covid19.view_model.DataState

class NewsAdapter(private val listener: ClickListener<News?>) :
    PagedListAdapter<News, NewsViewHolder>(newsDiffUtil) {

    var state = DataState.LOADING
        set(value) {
            field = value
            notifyItemChanged(itemCount)
        }

    companion object {
        const val DATA_TYPE = 1000
        const val FOOTER_TYPE = 9990

        val newsDiffUtil = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_news_item,
                parent,
                false
            ),
            listener
        )

    override fun getItemViewType(position: Int) =
        if (position < super.getItemCount()) DATA_TYPE else FOOTER_TYPE

    override fun getItemCount() = super.getItemCount() +
            if (hasFooter()) 1 else 0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_TYPE) {
            holder.bind(getItem(position), position == itemCount - 1)
        } else {
            holder.bind(null, position == itemCount - 1)
        }
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && state != DataState.SUCCESS
    }
}