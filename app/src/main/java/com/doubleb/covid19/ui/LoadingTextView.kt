package com.doubleb.covid19.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.doubleb.covid19.R

class LoadingTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun loading(){
        foreground = ContextCompat.getDrawable(context, R.color.loading)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        foreground = null
    }

}