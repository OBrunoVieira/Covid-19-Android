package com.doubleb.covid19.ui.view_holder

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.toNumber
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.listener.ClickListener
import kotlinx.android.synthetic.main.view_world_country_item.view.*

class CountriesViewHolder(
    itemView: View,
    listener: ClickListener<Country?>
) : RecyclerView.ViewHolder(itemView) {

    private val margin = itemView.resources.getDimension(R.dimen.world_bottom_spacing).toInt()
    private var country: Country? = null

    init {
        itemView.setOnClickListener {
            listener.onItemClicked(country, adapterPosition)
        }
    }

    fun bind(country: Country?, isLastPosition: Boolean) {
        this.country = country

        itemView.run {
            val layoutParams = (layoutParams as? RecyclerView.LayoutParams)
            layoutParams?.setMargins(
                layoutParams.leftMargin,
                layoutParams.topMargin,
                layoutParams.rightMargin,
                if (isLastPosition) margin else 0
            )

            val countryName = country?.name
            val countryCases = country?.cases

            Glide.with(context)
                .load(country?.countryInfo?.flagUrl)
                .circleCrop()
                .into(country_item_image_view_country)

            countryName?.let {
                country_item_text_view_name.setLoadedText(it)
                country_item_text_view_cases.setLoadedText(countryCases?.toNumber())

                country_text_view_badge.visibility = VISIBLE
                country_text_view_badge.text =
                    context.getString(R.string.position, adapterPosition + 1)
            } ?: run {
                country_item_text_view_name.loading()
                country_item_text_view_cases.loading()
                country_text_view_badge.visibility = INVISIBLE
            }
        }
    }
}