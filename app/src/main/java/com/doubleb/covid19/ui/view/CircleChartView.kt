package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.view_circle_chart.view.*

class CircleChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var activeCases = 0
    private var recoveredCases = 0
    private var criticalCases = 0
    private var totalCases = 0

    init {
        View.inflate(context, R.layout.view_circle_chart, this)
        loading()
    }

    fun loading(){
        circle_chart_critical_loading.progress = 100
        circle_chart_text_view_result.loading()
    }

    fun totalCases(totalCases: Int) = apply {
        this.totalCases = totalCases
    }

    fun cases(activeCases: Int, recoveredCases: Int, criticalCases: Int) = apply {
        this.activeCases = activeCases
        this.recoveredCases = recoveredCases
        this.criticalCases = criticalCases
    }

    fun build() {
        circle_chart_text_view_result.setLoadedText(totalCases.toString())

        circle_chart_critical_loading.progress = 0
        circle_chart_critical_active.progress = casesByTotal(activeCases)
        circle_chart_critical_recovered.progress =
            circle_chart_critical_active.progress + casesByTotal(recoveredCases)
    }

    private fun casesByTotal(cases: Int) =
        ((cases.toFloat() / totalCases.toFloat()) * 100).toInt()
}