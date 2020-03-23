package com.doubleb.covid19.ui.view_holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleb.covid19.model.Country
import com.doubleb.covid19.ui.listener.ClickListener

class SearchViewHolder(itemView: View, listener: ClickListener<Country>) :
    RecyclerView.ViewHolder(itemView) {

    var country: Country? = null

    init {
        itemView.setOnClickListener {
            listener.onItemClicked(country, adapterPosition)
        }
    }

    fun bind(country: Country?) {
        this.country = country

        val textView = itemView as TextView
        textView.text = country?.country
    }
}