package com.doubleb.covid19.ui

import android.content.Intent
import android.os.Bundle
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_menu_item_home.setOnClickListener {
            main_menu_item_world.alpha = .5f
            main_menu_item_home.alpha = 1f

            inflateFragment(R.id.main_fragment_container, HomeFragment(), HomeFragment.TAG)
        }

        main_menu_item_world.setOnClickListener {
            main_menu_item_home.alpha = .5f
            main_menu_item_world.alpha = 1f

            inflateFragment(R.id.main_fragment_container, WorldFragment(), WorldFragment.TAG)
        }

        main_float_action_button.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}