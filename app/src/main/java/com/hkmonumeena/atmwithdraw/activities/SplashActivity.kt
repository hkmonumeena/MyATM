package com.hkmonumeena.atmwithdraw.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hkmonumeena.atmwithdraw.R
import com.hkmonumeena.atmwithdraw.helper.Craft.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 1000)
    }
}