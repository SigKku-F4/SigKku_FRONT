package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.data.Change_info
import kotlinx.android.synthetic.main.activity_change_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeInfoActivity : AppCompatActivity() {

    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_info)

        (application as MasterApplication).service.getInfo(Token.token).enqueue(object :
            Callback<Change_info> {
            override fun onFailure(call: Call<Change_info>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Change_info>,
                response: Response<Change_info>
            ) {
                if (response.isSuccessful.not()) {
                    return
                }

                val nickname = response.body()?.nickname
                val age = response.body()?.age
                val height = response.body()?.height
                val weight = response.body()?.weight

                change_name.setText("${nickname}")
                change_age.setText("${age}")
                change_height.setText("${height}")
                change_goal.setText("${weight}")

                var gender = response.body()?.gender

                if (gender == "MALE")
                    change_gender_group.check(R.id.change_man)
                else
                    change_gender_group.check(R.id.change_woman)
            }

        })

        back_setting.setOnClickListener {
            var back_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(back_intent)
            finish()
        }

        btn_change_info.setOnClickListener {
            val nickname = change_name.text.toString()
            val age = change_age.text.toString()
            val height = change_height.text.toString()
            val weight = change_weight.text.toString()
            val goal = change_goal.text.toString()
            var gender =""

            if (change_man.isChecked == true)
                gender = "MALE"
            else if (change_woman.isChecked == true)
                gender = "FEMALE"
            else
                isExistBlank = true

            if (nickname.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || goal.isEmpty()){
                isExistBlank = true
            }
            if(!isExistBlank){
                (application as MasterApplication).service.changeInfo(Token.token, Change_info(nickname, gender, age.toInt(), height.toInt(), goal.toInt())).enqueue(object :
                    Callback<Change_info> {
                    override fun onFailure(call: Call<Change_info>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<Change_info>, response: Response<Change_info>) {
                        if (response.isSuccessful.not()) {
                            return
                        }
                    }
                })
                Toast.makeText(this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "빈 칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

    }
}