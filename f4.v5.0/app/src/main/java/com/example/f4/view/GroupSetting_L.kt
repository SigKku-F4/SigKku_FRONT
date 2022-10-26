package com.example.f4.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.data.*
import kotlinx.android.synthetic.main.activity_group_setting_l.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupSetting_L : AppCompatActivity() {

    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_setting_l)

        val id = intent.getLongExtra("groupId", 0)

        (application as MasterApplication).service.groupSettingL(Token.token, id).enqueue(object :
            Callback<Group_setting_l> {
            override fun onFailure(call: Call<Group_setting_l>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Group_setting_l>,
                response: Response<Group_setting_l>
            ) {
                if (response.isSuccessful.not()) {
                    return
                }

                val group_name = response.body()?.groupName
                val group_info = response.body()?.description
                val group_code = response.body()?.groupCode

                edit_group_name.setText("${group_name}")
                edit_group_intro.setText("${group_info}")
                copy_group_code.setText("${group_code}")


                var profile = response.body()?.profile

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

            val group_name = edit_group_name.text.toString()
            val group_info = edit_group_intro.text.toString()

            if (group_name.isEmpty() || group_info.isEmpty()) {
                isExistBlank = true
            }

            if (!isExistBlank) {
                (application as MasterApplication).service.groupSetChangeL(
                    Token.token,
                    id,
                    Group_set_change_l(group_name, profile, group_info, 4)
                ).enqueue(object :
                    Callback<Group_set_change_l> {
                    override fun onFailure(call: Call<Group_set_change_l>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<Group_set_change_l>,
                        response: Response<Group_set_change_l>
                    ) {
                        if (response.isSuccessful.not()) {
                            return
                        }
                    }
                })
                Toast.makeText(this, "변경되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "그룹명과 소개는 비울 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
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

        btn_group_disband.setOnClickListener {
            (application as MasterApplication).service.groupSetDeleteL(Token.token, id).enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<Boolean>, response: Response<Boolean>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                }
            })
            val intent = Intent(this, GroupActivity::class.java)
            intent.putExtra("Context", 1)
            startActivity(intent)
        }

        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("CODE", copy_group_code.getText().toString().trim())

        btn_copy.setOnClickListener {
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}