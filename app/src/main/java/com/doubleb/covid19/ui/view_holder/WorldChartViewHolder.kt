package com.doubleb.covid19.ui.view_holder

import android.view.View
import com.doubleb.covid19.model.Country
import kotlinx.android.synthetic.main.view_world_card_chart.view.*

class WorldChartViewHolder(itemView: View) : WorldViewHolder(itemView) {

    fun bind(country: Country?) {
        country?.let {
            itemView.world_chart_card_view
                .totalCases(it.cases)
                .activeCases(it.cases - it.recovered - it.deaths)
                .criticalCases(it.deaths)
                .recoveredCases(it.recovered)
                .build()
        } ?: run {
            itemView.world_chart_card_view.loading()
        }
    }
}