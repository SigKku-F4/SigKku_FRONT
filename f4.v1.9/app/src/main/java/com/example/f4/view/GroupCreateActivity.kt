package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.R
import kotlinx.android.synthetic.main.activity_group_create.*

class GroupCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_create)


        //        //뒤로가기
//        Backimg.setOnClickListener {
//            var intent= Intent(this,GroupActivity::class.java)
//            startActivity(intent)
//        }
    }
}