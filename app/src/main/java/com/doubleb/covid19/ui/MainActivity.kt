package com.doubleb.covid19.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_menu_item_home.setOnClickListener {
            main_menu_item_world.alpha = .5f
            main_menu_item_home.alpha = 1f

            inflateFragment(HomeFragment.TAG, HomeFragment())
        }

        main_menu_item_world.setOnClickListener {
            main_menu_item_home.alpha = .5f
            main_menu_item_world.alpha = 1f

            inflateFragment(WorldFragment.TAG, WorldFragment())
        }

        main_float_action_button.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun inflateFragment(tag: String, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment, tag)
            .commit()
    }
}