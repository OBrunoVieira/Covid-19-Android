package com.doubleb.covid19.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.view_care.view.*

class CareView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    @DrawableRes
    private var illustrationRes: Int = 0

    @ColorRes
    private var indicatorColor: Int = 0

    @StringRes
    private var titleRes: Int = 0

    @StringRes
    private var descriptionRes: Int = 0

    init {
        View.inflate(context, R.layout.view_care, this)
    }

    fun illustrationRes(@DrawableRes illustrationRes: Int) = apply {
        this.illustrationRes = illustrationRes
    }

    fun indicatorColor(@ColorRes indicatorColor: Int) = apply {
        this.indicatorColor = indicatorColor
    }

    fun titleRes(@StringRes titleRes: Int) = apply {
        this.titleRes = titleRes
    }

    fun descriptionRes(@StringRes descriptionRes: Int) = apply {
        this.descriptionRes = descriptionRes
    }

    fun build() {
        care_image_view_illustration.setBackgroundResource(illustrationRes)
        care_text_view_title.setText(titleRes)
        care_text_view_description.setText(descriptionRes)
        care_image_view_indicator.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(context, indicatorColor)
        )
    }
}