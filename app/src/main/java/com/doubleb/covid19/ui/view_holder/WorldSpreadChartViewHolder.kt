package com.doubleb.covid19.ui.view_holder

import android.view.View
import com.doubleb.covid19.model.HistoricalResult
import kotlinx.android.synthetic.main.view_world_spread_chart.view.*

class WorldSpreadChartViewHolder(itemView: View) : WorldViewHolder(itemView) {

    fun bind(historical: HistoricalResult?) {
        historical?.let {
            itemView.world_spread_chart_card_view
                .loadChart(
                    recovered = historical.recovered,
                    deaths = historical.deaths,
                    actives = historical.cases
                )
        } ?: run {
            itemView.world_spread_chart_card_view.loading()
        }
    }
}