package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        Group_Calender.setOnClickListener {
            var gc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(gc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Spoon.setOnClickListener {
            var gsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(gsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Group.setOnClickListener {
            var gg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(gg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Setting.setOnClickListener {
            var gs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(gs_intent)
            overridePendingTransition(0, 0);
            finish()

        }
    }


}