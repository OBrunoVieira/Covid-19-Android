package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.WorldData
import com.doubleb.covid19.ui.view.ChartCardView
import com.doubleb.covid19.ui.view_holder.WorldChartViewHolder
import com.doubleb.covid19.ui.view_holder.WorldCountriesViewHolder
import com.doubleb.covid19.ui.view_holder.WorldTitleViewHolder
import com.doubleb.covid19.ui.view_holder.WorldViewHolder

class WorldAdapter(val list: ArrayList<WorldData> = ArrayList()) :
    RecyclerView.Adapter<WorldViewHolder>() {
    companion object {
        private const val VIEW_TYPE_TITLE = 1111
        private const val VIEW_TYPE_CHART = 1112
        private const val VIEW_TYPE_COUNTRIES = 1113
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            VIEW_TYPE_TITLE ->
                WorldTitleViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_world_title,
                        parent,
                        false
                    )
                )

            VIEW_TYPE_CHART ->
                WorldChartViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_world_card_chart,
                        parent,
                        false
                    )
                )

            else ->
                WorldCountriesViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_world_country_item,
                        parent,
                        false
                    )
                )
        }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        val country = list[position].country
        when (getItemViewType(position)) {
            VIEW_TYPE_CHART -> {
                (holder as WorldChartViewHolder).bind(country)
            }

            VIEW_TYPE_COUNTRIES -> {
                (holder as WorldCountriesViewHolder).bind(country, position == list.size -1)
            }
        }
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> VIEW_TYPE_TITLE

            1 -> VIEW_TYPE_CHART

            else -> VIEW_TYPE_COUNTRIES
        }
}