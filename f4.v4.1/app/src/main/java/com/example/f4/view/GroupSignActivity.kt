package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.data.Groupid
import com.example.f4.data.Groupsign
import kotlinx.android.synthetic.main.activity_calender.*
import kotlinx.android.synthetic.main.activity_group_create.*
import kotlinx.android.synthetic.main.activity_group_sign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log
import kotlin.math.sign

class GroupSignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_sign)

//        //뒤로가기
        val backBtn = findViewById<ImageView>(R.id.groupSignBackBtn)
        backBtn.setOnClickListener {
            var intent= Intent(this,GroupActivity::class.java)
            startActivity(intent)
        }

        val groupSignBtn = findViewById<Button>(R.id.btn_groupSign)
        val btn_profile1 = findViewById<RadioButton>(R.id.btn_profile1)
        val btn_profile2 = findViewById<RadioButton>(R.id.btn_profile2)
        val btn_profile3 = findViewById<RadioButton>(R.id.btn_profile3)
        val btn_profile4 = findViewById<RadioButton>(R.id.btn_profile4)




        groupSignBtn.setOnClickListener {

            Log.d("log", "버튼 눌림")
            //통신
            val signGroupCode = signGroupCode.text.toString()
            //    val signGroupCode = "76c95b9b-d18a-40d7-bb7e-15e625f81380"
            var profile = "DONG"
            if (btn_profile1.isChecked == true)
                profile = "BEE"
            else if (btn_profile2.isChecked == true)
                profile = "DONG"
            else if (btn_profile3.isChecked == true)
                profile = "YOU"
            else
                profile = "SO"

            (application as MasterApplication).service.groupSign(Token.token, Groupsign(signGroupCode, profile)).enqueue(object :
                Callback<Groupid> {
                override fun onFailure(call: Call<Groupid>, t: Throwable) {
                    Log.d("TEST fail : ", "fail")
                }
                override fun onResponse(call: Call<Groupid>, response: Response<Groupid>) {
                    if (response.isSuccessful.not()) {
                        Log.d("Test fail : ", "response")
                        return
                    }
                    val groupId = response.body()
                    Log.d("TEST response : ", "groupId : ${groupId?.groupId}")
                }
            })

            var intent= Intent(this,GroupActivity::class.java)
            startActivity(intent)
        }

    }
}