package com.doubleb.covid19.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doubleb.covid19.ui.fragment.PreventionFragment
import com.doubleb.covid19.ui.fragment.SymptomsFragment

class CareAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
        if (position == 0) {
            PreventionFragment()
        } else {
            SymptomsFragment()
        }
}