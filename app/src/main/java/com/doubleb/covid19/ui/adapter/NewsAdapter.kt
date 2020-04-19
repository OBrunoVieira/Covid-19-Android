package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.News
import com.doubleb.covid19.ui.view_holder.NewsViewHolder

class NewsAdapter(val list: ArrayList<News> = ArrayList()) :
    RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_news_item,
                parent,
                false
            )
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position], position == list.size - 1)
    }
}