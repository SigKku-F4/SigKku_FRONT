package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_calender.*

class CalenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        Calender_Calender.setOnClickListener {
            var cc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(cc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Spoon.setOnClickListener {
            var csp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(csp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Group.setOnClickListener {
            var cg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(cg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Setting.setOnClickListener {
            var cs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(cs_intent)
            overridePendingTransition(0, 0);
            finish()

        }
    }
}