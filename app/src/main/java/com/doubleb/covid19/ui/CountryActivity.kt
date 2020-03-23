package com.doubleb.covid19.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.activity_country.*

class CountryActivity : AppCompatActivity(R.layout.activity_country) {

    companion object {
        const val ARGUMENTS_COUNTRY_NAME = "ARGUMENTS_COUNTRY_NAME"
    }

    private var countryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryName = intent.getStringExtra(ARGUMENTS_COUNTRY_NAME) ?: getString(R.string.country)

        country_image_view_back.setOnClickListener { onBackPressed() }
        country_text_view_title.text = countryName

        supportFragmentManager.beginTransaction()
            .replace(R.id.country_fragment_container, HomeFragment.newInstance(countryName, true))
            .commit()
    }
}