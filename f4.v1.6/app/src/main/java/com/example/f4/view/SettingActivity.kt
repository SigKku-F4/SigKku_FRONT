package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_change_info.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_spoon.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        Setting_Calender.setOnClickListener {
            var sc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(sc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Setting_Spoon.setOnClickListener {
            var ssp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(ssp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Setting_Group.setOnClickListener {
            var sg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(sg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Setting_Setting.setOnClickListener {
            var ss_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(ss_intent)
            overridePendingTransition(0, 0);
            finish()

        }

        change_info.setOnClickListener {
            var changeInfo_intent: Intent = Intent(this, ChangeInfoActivity::class.java)
            startActivity(changeInfo_intent)
            finish()
        }
    }
}