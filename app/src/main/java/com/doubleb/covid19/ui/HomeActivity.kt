package com.doubleb.covid19.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleb.covid19.view_model.HomeViewModel
import com.doubleb.covid19.R
import com.doubleb.covid19.view_model.DataSource
import com.doubleb.covid19.view_model.DataState
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.lifecycleScope

class HomeActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel.liveData.observe(this, observeCountry())
        viewModel.getByCountry()
    }

    private fun observeCountry() = Observer<DataSource<Any>> {
        when(it.dataState){
            DataState.LOADING -> {
                home_text_view.text = "loading"
            }

            DataState.SUCCESS -> {
                home_text_view.text = it.data.toString()
            }

            DataState.ERROR ->{
                home_text_view.text = "erro"
            }
        }
    }

}