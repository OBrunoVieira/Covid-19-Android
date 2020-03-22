package com.doubleb.covid19.ui.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.R
import com.doubleb.covid19.model.Country
import kotlinx.android.synthetic.main.view_world_country_item.view.*

class WorldCountriesViewHolder(itemView: View) : WorldViewHolder(itemView) {

    private val margin = itemView.resources.getDimension(R.dimen.world_bottom_spacing).toInt()

    fun bind(country: Country?, isLastPosition: Boolean) {
        val layoutParams = (itemView.layoutParams as? RecyclerView.LayoutParams)
        layoutParams?.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            if (isLastPosition) margin else 0
        )

        country?.let {
            itemView.country_item_text_view_name.setLoadedText(it.country)
            itemView.country_item_text_view_cases.setLoadedText(it.cases.toString())
        } ?: run {
            itemView.country_item_text_view_name.loading()
            itemView.country_item_text_view_cases.loading()
        }
    }
}