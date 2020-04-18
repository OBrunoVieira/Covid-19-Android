package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.toNumber
import kotlinx.android.synthetic.main.view_card_chart.view.*
import java.text.NumberFormat

class ChartCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var totalCases = 0
    private var activeCases = 0
    private var recoveredCases = 0
    private var criticalCases = 0

    init {
        View.inflate(context, R.layout.view_card_chart, this)
    }

    fun loading() {
        card_chart_circle_chart_view.loading()
        card_chart_text_view_active_cases_number.loading()
        card_chart_text_view_recovered_cases_number.loading()
        card_chart_text_view_critical_cases_number.loading()
    }

    fun totalCases(totalCases: Int) = apply {
        this.totalCases = totalCases
    }

    fun activeCases(activeCases: Int) = apply {
        this.activeCases = activeCases
    }

    fun recoveredCases(recoveredCases: Int) = apply {
        this.recoveredCases = recoveredCases
    }

    fun deathCases(criticalCases: Int) = apply {
        this.criticalCases = criticalCases
    }

    fun build() {
        card_chart_circle_chart_view
            .cases(activeCases, recoveredCases, criticalCases)
            .totalCases(totalCases)
            .build()

        card_chart_text_view_active_cases_number.setLoadedText(activeCases.toNumber())
        card_chart_text_view_recovered_cases_number.setLoadedText(recoveredCases.toNumber())
        card_chart_text_view_critical_cases_number.setLoadedText(criticalCases.toNumber())
    }

}