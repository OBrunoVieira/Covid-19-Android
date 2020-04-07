package com.doubleb.covid19.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.doubleb.covid19.R
import com.doublebb.tracking.ScreenName
import com.doublebb.tracking.Tracking

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Tracking.sendScreenView(this, ScreenName.SPLASH)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
}