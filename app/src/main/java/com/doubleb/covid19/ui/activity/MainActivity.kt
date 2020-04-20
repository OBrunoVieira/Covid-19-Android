package com.doubleb.covid19.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.fragment.app.Fragment
import com.doubleb.covid19.R
import com.doubleb.covid19.ui.fragment.CareFragment
import com.doubleb.covid19.ui.fragment.HomeFragment
import com.doubleb.covid19.ui.fragment.NewsFragment
import com.doubleb.covid19.ui.fragment.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private var fragmentTag = HomeFragment::class.java.name

    companion object {
        private const val FRAGMENT_TAG_STATE = "FRAGMENT_TAG_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            inflateFragment(HomeFragment())
        } else {
            fragmentTag = savedInstanceState.getString(FRAGMENT_TAG_STATE, fragmentTag)
            selectViewByTag(fragmentTag)
        }

        configureClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_TAG_STATE, fragmentTag)
    }

    private fun configureClickListeners() {
        main_menu_item_home.setOnClickListener {
            inflateFragment(HomeFragment())
        }

        main_menu_item_world.setOnClickListener {
            inflateFragment(WorldFragment())
        }

        main_menu_item_news.setOnClickListener {
            inflateFragment(NewsFragment())
        }

        main_menu_item_care.setOnClickListener {
            inflateFragment(CareFragment())
        }

        main_float_action_button.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun inflateFragment(fragment: Fragment) {
        inflateFragment(R.id.main_fragment_container, fragment)
        selectViewByTag(fragment.javaClass.name)
    }

    private fun selectViewByTag(tag: String) {
        this.fragmentTag = tag

        when (tag) {
            HomeFragment::class.java.name -> {
                selectView(main_menu_item_home)
            }

            WorldFragment::class.java.name -> {
                selectView(main_menu_item_world)
            }

            NewsFragment::class.java.name -> {
                selectView(main_menu_item_news)
            }

            else -> {
                selectView(main_menu_item_care)
            }
        }
    }

    private fun selectView(targetView: View) {
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