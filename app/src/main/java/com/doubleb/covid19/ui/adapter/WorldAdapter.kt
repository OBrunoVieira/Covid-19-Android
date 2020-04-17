package com.doubleb.covid19.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doubleb.covid19.ui.fragment.CountriesFragment
import com.doubleb.covid19.ui.fragment.OverviewFragment

class WorldAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
        if (position == 0) {
            OverviewFragment()
        } else {
            CountriesFragment()
        }

}