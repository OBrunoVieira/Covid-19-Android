package com.doubleb.covid19.ui.activity

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    fun inflateFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        val tag = fragment.javaClass.name

        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commit()
        }
    }
}