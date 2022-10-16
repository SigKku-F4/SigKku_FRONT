package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.data.*
import kotlinx.android.synthetic.main.activity_group_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max

class GroupCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_create)

        val backBtn = findViewById<ImageView>(R.id.groupCreateBackBtn)
        backBtn.setOnClickListener {
            val intent= Intent(this,GroupActivity::class.java)
            startActivity(intent)
        }

        var profile = ""


        val groupSignBtn = findViewById<Button>(R.id.btn_groupCreate)
        groupSignBtn.setOnClickListener {
            //통신
            val createGroupName = createGroupName.text.toString()
            val createGroupDesc = createGroupDesc.text.toString()
            if (btn_profile1.isChecked == true)
                profile = "BEE"
            else if (btn_profile2.isChecked == true)
                profile = "DONG"
            else if (btn_profile3.isChecked == true)
                profile = "YOU"
            else
                profile = "SO"

            val groupSize = 4

            (application as MasterApplication).service.groupCreate(Token.token, Groupinfo( createGroupName.toString(),  createGroupDesc.toString(), groupSize, profile)).enqueue(object :
                Callback<Groupid> {
                override fun onFailure(call: Call<Groupid>, t: Throwable) {
                    Log.d("TEST fail : ", "fail")
                }
                override fun onResponse(call: Call<Groupid>, response: Response<Groupid>) {
                    if (response.isSuccessful.not()) {
                        Log.d("Test fail : ", "response")
                        Log.d("TEST", " errorBody: ${response.errorBody()?.string()}")

                        return
                    }
                    val groupId: Groupid? = response.body()
                    Log.d("TEST response : ", "${groupId?.groupId}")
                }
            })

            val intent= Intent(this,GroupActivity::class.java)
            startActivity(intent)
        }


    }
}