package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.view_today_cases.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TodayCasesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    companion object {
        private const val DATE_FORMAT = "(dd/MM/yy)"
    }

    private var todayCases = 0
    private var todayDeaths = 0

    init {
        View.inflate(context, R.layout.view_today_cases, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun loading() {
        today_cases_text_view_newest_results.loading()
        today_cases_text_view_newest_deaths_results.loading()
        today_cases_text_view_date.loading()
    }

    fun todayCases(todayCases: Int) = apply {
        this.todayCases = todayCases
    }

    fun todayDeaths(todayDeaths: Int) = apply {
        this.todayDeaths = todayDeaths
    }

    fun build() {
        today_cases_text_view_date.setLoadedText(
            SimpleDateFormat(
                DATE_FORMAT,
                Locale.getDefault()
            ).format(Date())
        )

        today_cases_text_view_newest_results.setLoadedText(NumberFormat.getInstance().format(todayCases))
        today_cases_text_view_newest_deaths_results.setLoadedText(NumberFormat.getInstance().format(todayDeaths))
    }

}