package com.example.f4.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.sip.SipErrorCode
import android.net.sip.SipErrorCode.TIME_OUT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private var TIME_OUT:Long = 1000

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val  ani = AnimationUtils.loadAnimation(this, R.anim.progress)
        progress.startAnimation(ani)

        loadSplashScreen()
    }

    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, R.anim.fadeout);
            finish()
        }, TIME_OUT)
    }
}