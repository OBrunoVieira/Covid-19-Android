package com.doubleb.covid19.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.model.BaseData
import com.doubleb.covid19.ui.listener.ClickListener
import com.doubleb.covid19.ui.view_holder.*

class WorldAdapter(
    val list: ArrayList<BaseData> = ArrayList(),
    val listener: ClickListener<Country?>
) :
    RecyclerView.Adapter<WorldViewHolder>() {
    companion object {
        private const val VIEW_TYPE_TITLE = 1111
        private const val VIEW_TYPE_CIRCLE_CHART = 1112
        private const val VIEW_TYPE_SPREAD_CHART = 1113
        private const val VIEW_TYPE_COUNTRIES = 1114
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

            VIEW_TYPE_CIRCLE_CHART ->
                WorldChartViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_world_card_chart,
                        parent,
                        false
                    )
                )

            VIEW_TYPE_SPREAD_CHART ->
                WorldSpreadChartViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_world_spread_chart,
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
                    ),
                    listener
                )
        }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        val country = list[position].country
        val historical = list[position].historical
        when (getItemViewType(position)) {
            VIEW_TYPE_CIRCLE_CHART -> {
                (holder as WorldChartViewHolder).bind(country)
            }

            VIEW_TYPE_SPREAD_CHART -> {
                (holder as WorldSpreadChartViewHolder).bind(historical)
            }

            VIEW_TYPE_COUNTRIES -> {
                (holder as WorldCountriesViewHolder).bind(country, position == list.size - 1)
            }
        }
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> VIEW_TYPE_TITLE

            1 -> VIEW_TYPE_CIRCLE_CHART

            2 -> VIEW_TYPE_SPREAD_CHART

            else -> VIEW_TYPE_COUNTRIES
        }
}