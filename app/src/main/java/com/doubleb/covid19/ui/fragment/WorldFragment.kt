package com.doubleb.covid19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.adapter.WorldAdapter
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_world.*

class WorldFragment : Fragment() {

    companion object {
        const val TAG = "WorldFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_world, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        world_view_pager.adapter = WorldAdapter(this)
        world_view_pager.visibility = VISIBLE

        TabLayoutMediator(world_tab_layout, world_view_pager) { tab, position ->
            tab.setText(if (position == 0) R.string.overview_title else R.string.countries_title)
        }.attach()
    }
}