package com.doubleb.covid19.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.doubleb.covid19.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.NumberFormat


class CircleChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PieChart(context, attrs, defStyleAttr) {

    private var activeCases = 0
    private var recoveredCases = 0
    private var criticalCases = 0
    private var totalCases = 0
    private var thereWasChanging = false

    init {

        minimumWidth = resources.getDimension(R.dimen.circle_chart_view_size).toInt()
        minimumHeight = resources.getDimension(R.dimen.circle_chart_view_size).toInt()

        holeRadius = 80f
        transparentCircleRadius = 83f

        description.isEnabled = false
        legend.isEnabled = false

        isDragDecelerationEnabled = false
        setTouchEnabled(false)

        setTransparentCircleColor(Color.WHITE)
        setTransparentCircleAlpha(110)

        setDrawCenterText(true)
        invalidate()

        loading()
    }

    fun loading() {
        centerText = configureLoadingText()
        data = PieData(configurePieLoadingData())
    }

    fun totalCases(totalCases: Int) = apply {
        this.thereWasChanging = this.totalCases != totalCases
        this.totalCases = totalCases
    }

    fun cases(activeCases: Int, recoveredCases: Int, criticalCases: Int) = apply {
        this.activeCases = activeCases
        this.recoveredCases = recoveredCases
        this.criticalCases = criticalCases
    }

    fun build() {
        if (thereWasChanging) {
            animateY(800, Easing.EaseInOutQuad)
            spin(1500, 0f, 270f, Easing.EaseInOutQuad)
        }

        centerText = configureCenteredText(totalCases)
        data = PieData(
            configurePieData(
                infectedRate = casesByTotal(activeCases),
                recoveredRate = casesByTotal(recoveredCases),
                fatalityRate = casesByTotal(criticalCases)
            )
        )
    }

    private fun casesByTotal(cases: Int) =
        (cases.toFloat() / totalCases.toFloat()) * 100

    private fun configureLoadingText() = run {
        val title = context.getString(R.string.circle_chart_title)
        SpannableString(title).apply {
            setSpan(
                WallSpannable(ContextCompat.getColor(context, R.color.loading)),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                RelativeSizeSpan(2f),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun configureCenteredText(totalCases: Int) = run {
        val title = context.getString(R.string.circle_chart_title)
        val result = NumberFormat.getInstance().format(totalCases)
        val nunitoRegular = ResourcesCompat.getFont(context, R.font.nunito_regular)
        val nunitoBold = ResourcesCompat.getFont(context, R.font.nunito_bold)

        SpannableString("${title}\n${result}").apply {
            //============================ Span Title Text =========================================
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.black)),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                TypeFaceSpannable(nunitoRegular),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            //============================ Span Result Text =========================================
            setSpan(
                RelativeSizeSpan(1.8f),
                title.length,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                TypeFaceSpannable(nunitoBold),
                title.length,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun configurePieLoadingData() = run {
        val entries = ArrayList<PieEntry>().apply {
            add(PieEntry(100f))
        }

        PieDataSet(entries, null).apply {
            setColors(ContextCompat.getColor(context, R.color.loading))
            setDrawValues(false)
        }
    }

    private fun configurePieData(infectedRate: Float, recoveredRate: Float, fatalityRate: Float) =
        run {
            val entries = ArrayList<PieEntry>().apply {
                add(PieEntry(infectedRate))
                add(PieEntry(recoveredRate))
                add(PieEntry(fatalityRate))
            }

            val colors = ArrayList<Int>().apply {
                add(ContextCompat.getColor(context, R.color.yellow_light))
                add(ContextCompat.getColor(context, R.color.green_dark))
                add(ContextCompat.getColor(context, R.color.black))
            }

            PieDataSet(entries, null).apply {
                setColors(colors)
                setDrawValues(false)
            }
        }


    private inner class TypeFaceSpannable(private val typeFace: Typeface?) : MetricAffectingSpan() {

        override fun updateMeasureState(textPaint: TextPaint) {
            applyTypeFace(textPaint, typeFace)
        }

        override fun updateDrawState(textPaint: TextPaint?) {
            textPaint?.let { applyTypeFace(textPaint, typeFace) }
        }

        private fun applyTypeFace(paint: Paint, typeFace: Typeface?) {
            paint.typeface = typeFace
        }

    }

    private inner class WallSpannable(@ColorInt val color: Int) : ForegroundColorSpan(color) {

        override fun updateDrawState(textPaint: TextPaint) {
            super.updateDrawState(textPaint)
            textPaint.bgColor = color
            textPaint.color = color
        }
    }
}