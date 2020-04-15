package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.ui.view_holder.*

class CountriesAdapter(
    val list: ArrayList<Country> = ArrayList(),
    val listener: ClickListener<Country?>
) :
    RecyclerView.Adapter<CountriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountriesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_world_country_item,
                parent,
                false
            ),
            listener
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = list[position]
        holder.bind(country, position == list.size - 1)
    }
}