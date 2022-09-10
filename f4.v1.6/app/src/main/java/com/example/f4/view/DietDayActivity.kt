package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_group_create.*

class DietDayActivity : AppCompatActivity() {

    var dateId : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_day)

        dateId = findViewById<TextView>(R.id.SelectedDateId)

        val select_date = intent.getStringExtra("selectDate")

        dateId?.setText(select_date)

 //       Toast.makeText(this, select_date, Toast.LENGTH_SHORT).show() //날짜 터치시 알림표시로 나타내기

    }
}