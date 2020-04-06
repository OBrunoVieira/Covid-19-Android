package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.takeLast
import com.doubleb.covid19.model.TimeLineResult
import kotlinx.android.synthetic.main.view_card_spread_chart.view.*
import java.text.SimpleDateFormat
import java.util.*

class SpreadCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    companion object {
        private const val DATE_FORMAT = "dd/MM"
    }

    init {
        View.inflate(context, R.layout.view_card_spread_chart, this)
    }

    fun loading() {
        card_spread_chart_text_view_date.loading()
        card_spread_chart_view.loading()
    }

    fun loadChart(
        recovered: List<TimeLineResult>,
        deaths: List<TimeLineResult>,
        actives: List<TimeLineResult>
    ) {
        val parse = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val lastSevenDays = parse.format(Calendar.getInstance().takeLast(7))
        val today = parse.format(Date())

        card_spread_chart_text_view_date.setLoadedText("($lastSevenDays-$today)")
        card_spread_chart_view
            .recovered(recovered)
            .deaths(deaths)
            .actives(actives)
            .build()
    }
}