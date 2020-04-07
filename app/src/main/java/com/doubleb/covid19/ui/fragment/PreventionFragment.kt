package com.doubleb.covid19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.adapter.CareListAdapter
import com.doubleb.covid19.ui.adapter.CareListAdapter.Preventions
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import kotlinx.android.synthetic.main.fragment_preventions.*

class PreventionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_preventions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.PREVENTIONS)
        preventions_recycler_view.adapter = CareListAdapter<Preventions>(enumValues())
    }
}