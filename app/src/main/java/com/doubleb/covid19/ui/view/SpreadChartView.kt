package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.shortValue
import com.doubleb.covid19.model.TimeLineResult
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter


class SpreadChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BarChart(context, attrs, defStyleAttr) {

    private val barSpace = 0.02f //x3
    private val barWidth = 0.18f //x3
    private val groupSpace = 1f - ((barWidth + barSpace) * 3)
    private val groupCount = 7f

    private var recovered = listOf<TimeLineResult>()
    private var deaths = listOf<TimeLineResult>()
    private var actives = listOf<TimeLineResult>()

    init {
        minimumHeight = resources.getDimension(R.dimen.spread_chart_view_size).toInt()

        description.isEnabled = false
        legend.isEnabled = false

        isDragDecelerationEnabled = false
        setTouchEnabled(false)

        xAxis.axisMinimum = 0f
        xAxis.isEnabled = false

        axisLeft.axisMinimum = 0f
        axisLeft.setDrawAxisLine(false)
        axisLeft.setLabelCount(5, true)

        axisRight.isEnabled = false

        loading()
    }

    fun recovered(recovered: List<TimeLineResult>) = apply {
        this.recovered = recovered
    }

    fun deaths(deaths: List<TimeLineResult>) = apply {
        this.deaths = deaths
    }

    fun actives(actives: List<TimeLineResult>) = apply {
        this.actives = actives
    }

    fun loading() {
        axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return ""
            }
        }

        buildData(
            configureLoadingChart()
        )
    }

    fun build() {
        axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value <= 0) "" else value.toLong().shortValue()
            }
        }

        buildData(
            configureDataChart(
                recovered = recovered,
                deaths = deaths,
                actives = actives
            )
        )
    }

    private fun buildData(data: BarData) {
        this.data = data
        barData.barWidth = barWidth
        groupBars(0f, groupSpace, barSpace)
        xAxis.axisMaximum = 0 + barData.getGroupWidth(groupSpace, barSpace) * groupCount

        invalidate()
    }

    private fun configureLoadingChart() = run {
        val recoveredValues: ArrayList<BarEntry> = ArrayList()
        val criticalValues: ArrayList<BarEntry> = ArrayList()
        val activeValues: ArrayList<BarEntry> = ArrayList()

        for (i in 0 until 7) {
            activeValues.add(BarEntry(i.toFloat(), 8f))
            recoveredValues.add(BarEntry(i.toFloat(), 5f))
            criticalValues.add(BarEntry(i.toFloat(), 2f))
        }

        val recoveredSet = loadingDataSet(recoveredValues)
        val activeSet = loadingDataSet(activeValues)
        val criticalSet = loadingDataSet(criticalValues)

        BarData(criticalSet, recoveredSet, activeSet)
    }

    private fun configureDataChart(
        recovered: List<TimeLineResult>,
        deaths: List<TimeLineResult>,
        actives: List<TimeLineResult>
    ) = run {
        val recoveredValues: ArrayList<BarEntry> = ArrayList()
        val criticalValues: ArrayList<BarEntry> = ArrayList()
        val activeValues: ArrayList<BarEntry> = ArrayList()

        recovered.forEachIndexed { index, timeLineResult ->
            recoveredValues.add(BarEntry(index.toFloat(), timeLineResult.cases.toFloat()))
        }

        deaths.forEachIndexed { index, timeLineResult ->
            criticalValues.add(BarEntry(index.toFloat(), timeLineResult.cases.toFloat()))
        }

        actives.forEachIndexed { index, timeLineResult ->
            activeValues.add(BarEntry(index.toFloat(), timeLineResult.cases.toFloat()))
        }

        val recoveredSet = BarDataSet(recoveredValues, null)
            .apply {
                color = ContextCompat.getColor(context, R.color.green_dark)
                setDrawValues(false)
            }

        val criticalSet = BarDataSet(criticalValues, null)
            .apply {
                color = ContextCompat.getColor(context, R.color.black)
                setDrawValues(false)
            }

        val activeSet = BarDataSet(activeValues, null)
            .apply {
                color = ContextCompat.getColor(context, R.color.yellow_light)
                setDrawValues(false)
            }

        BarData(criticalSet, recoveredSet, activeSet)
    }

    private fun loadingDataSet(list: List<BarEntry>) =
        BarDataSet(list, null)
            .apply {
                color = ContextCompat.getColor(context, R.color.loading)
                setDrawValues(false)
            }
}