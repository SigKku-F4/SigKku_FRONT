package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_diet_day.*
import kotlinx.android.synthetic.main.activity_group_create.*
import kotlinx.android.synthetic.main.activity_group_create.btn_add_diet
import kotlinx.android.synthetic.main.activity_setting.*

class DietDayActivity : AppCompatActivity() {

    var dateId : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_day)

//        btn_add_diet.setOnClickListener {
//            var addDiet_intent: Intent = Intent(this, RecordActivity::class.java)
//            startActivity(addDiet_intent)
//            finish()
//        }

        dateId = findViewById<TextView>(R.id.SelectedDateId)

        val select_date = intent.getStringExtra("selectDate")

        dateId?.setText(select_date)

        //data 받아오기

        //stamp

 //       Toast.makeText(this, select_date, Toast.LENGTH_SHORT).show() //날짜 터치시 알림표시로 나타내기

    }
}