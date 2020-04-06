package com.doubleb.covid19.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.fragment.CareFragment
import com.doubleb.covid19.ui.fragment.HomeFragment
import com.doubleb.covid19.ui.fragment.NewsFragment
import com.doubleb.covid19.ui.fragment.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_menu_item_home.setOnClickListener {
            selectView(it)
            inflateFragment(
                R.id.main_fragment_container,
                HomeFragment(), HomeFragment.TAG
            )
        }

        main_menu_item_world.setOnClickListener {
            selectView(it)
            inflateFragment(
                R.id.main_fragment_container,
                WorldFragment(), WorldFragment.TAG
            )
        }

        main_menu_item_news.setOnClickListener {
            selectView(it)
            inflateFragment(
                R.id.main_fragment_container,
                NewsFragment(), NewsFragment.TAG
            )
        }

        main_menu_item_care.setOnClickListener {
            selectView(it)
            inflateFragment(
                R.id.main_fragment_container,
                CareFragment(), CareFragment.TAG
            )
        }

        main_float_action_button.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    fun selectView(targetView: View) {
        val idList = arrayListOf(
            R.id.main_menu_item_home,
            R.id.main_menu_item_world,
            R.id.main_menu_item_news,
            R.id.main_menu_item_care
        )

        idList.forEach {
            val view = findViewById<View>(it)
            view.alpha = .5f
        }

        targetView.alpha = 1f
    }
}