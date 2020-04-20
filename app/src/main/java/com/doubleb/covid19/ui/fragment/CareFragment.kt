package com.doubleb.covid19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.adapter.CareAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_care.*

class CareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_care, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        care_view_pager.adapter = CareAdapter(this)
        care_view_pager.visibility = VISIBLE

        TabLayoutMediator(care_tab_layout, care_view_pager) { tab, position ->
            tab.setText(if (position == 0) R.string.prevention_title else R.string.symptoms_title)
        }.attach()
    }
}