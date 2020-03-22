package com.doubleb.covid19.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_menu_item_home.setOnClickListener {
            main_menu_item_world.alpha = .5f
            main_menu_item_home.alpha = 1f

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, HomeFragment())
                .commit()
        }

        main_menu_item_world.setOnClickListener {
            main_menu_item_home.alpha = .5f
            main_menu_item_world.alpha = 1f

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, WorldFragment())
                .commit()
        }
    }

}