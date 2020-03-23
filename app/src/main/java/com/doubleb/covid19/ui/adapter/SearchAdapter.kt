package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.ui.view_holder.SearchViewHolder

class SearchAdapter(
    val list: ArrayList<Country> = ArrayList(),
    private val listener : ClickListener<Country>
) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_search_item,
                parent,
                false
            ),
            listener
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position])
    }
}