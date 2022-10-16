package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.data.Group_setting_m
import kotlinx.android.synthetic.main.activity_group_setting_l.*
import kotlinx.android.synthetic.main.activity_group_setting_l.back_groupsetting
import kotlinx.android.synthetic.main.activity_group_setting_l.btn_group_setting_save
import kotlinx.android.synthetic.main.activity_group_setting_l.btn_profile1
import kotlinx.android.synthetic.main.activity_group_setting_l.btn_profile2
import kotlinx.android.synthetic.main.activity_group_setting_l.btn_profile3
import kotlinx.android.synthetic.main.activity_group_setting_l.radioGroup
import kotlinx.android.synthetic.main.activity_group_setting_m.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupSetting_M : AppCompatActivity() {

    var isExistBlank = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_setting_m)

        val id = intent.getLongExtra("groupId", 0)

       (application as MasterApplication).service.groupSettingM(Token.token, id).enqueue(object :
            Callback<Group_setting_m> {
            override fun onFailure(call: Call<Group_setting_m>, t: Throwable) {
                Log.d("@TEST fail : ", "통신 실패")
            }

            override fun onResponse(
                call: Call<Group_setting_m>, response: Response<Group_setting_m>
            ) {
                if (response.isSuccessful.not()) {
                    Log.d("@TEST fail : ", "응답 실패")
                    return
                }

                var profile = response.body()?.profile
                Log.d("TEST-------", "${profile}")

                if (profile == "BEE")
                    radioGroup.check(R.id.btn_profile1)
                else if (profile == "DONG")
                    radioGroup.check(R.id.btn_profile2)
                else if (profile == "YOU")
                    radioGroup.check(R.id.btn_profile3)
                else
                    radioGroup.check(R.id.btn_profile4)
            }

        })

        var profile = ""

        btn_group_setting_save.setOnClickListener {
            if (btn_profile1.isChecked == true)
                profile = "BEE"
            else if (btn_profile2.isChecked == true)
                profile = "DONG"
            else if (btn_profile3.isChecked == true)
                profile = "YOU"
            else
                profile = "SO"

            (application as MasterApplication).service.groupSetChangeM(Token.token, id,
                Group_setting_m(profile, true)
            ).enqueue(object :
                Callback<Group_setting_m> {
                override fun onFailure(call: Call<Group_setting_m>, t: Throwable) {
                    Log.d("CHANGE fail", "fail")
                }

                override fun onResponse(call: Call<Group_setting_m>, response: Response<Group_setting_m>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d("CHANGE fail", "response")
                        return
                    }
                    Log.d("CHANGE response", "성공~")
                }
            })
            Toast.makeText(this, "변경되었습니다.", Toast.LENGTH_SHORT).show()
        }

        btn_group_leave.setOnClickListener {
            (application as MasterApplication).service.groupSetDeleteM(Token.token, id).enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("DELETE fail", "fail")
                }
                override fun onResponse(
                    call: Call<Boolean>, response: Response<Boolean>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d("DELETE fail", "response")
                        return
                    }
                    Log.d("DELETE response", "성공~")
                }
            })
            val intent = Intent(this, GroupActivity::class.java)
            intent.putExtra("Context", 2)
            startActivity(intent)
        }


        var groupCur: Int = 0
        if(intent.hasExtra("groupCur")) {
            groupCur = intent.getIntExtra("groupCur", 0) // groupCurrent
        }

        back_groupsetting.setOnClickListener {
            val grintent = Intent(this, MyGroupActivity::class.java)
            grintent.putExtra("groupId", id) // groupId
            grintent.putExtra("groupCur", groupCur) // groupCurrent
            startActivity(grintent)
            finish()
        }
    }
}