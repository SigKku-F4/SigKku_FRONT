package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_change_info.*
import kotlinx.android.synthetic.main.activity_sign.*

class ChangeInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_info)

        back_setting.setOnClickListener {
            var back_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(back_intent)
            finish()
        }

        btn_change_info.setOnClickListener {
            var save_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(save_intent)
            finish()
        }

    }
}