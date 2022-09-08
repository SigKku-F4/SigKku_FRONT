package com.example.f4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.databinding.ActivityMyGroupBinding

class MyGroupActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMyGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 백버튼 클릭 시 그룹 메인화면으로 이동
        mBinding.BackMyGroup.setOnClickListener {
            startActivity(Intent(this@MyGroupActivity, GroupMainActivity::class.java))
            finish()
        }

        // 그룹 설정 버튼 클릭 시 설정화면으로 넘어감 (???에 넘어갈 액티비티 이름 적기)
//        mBinding.grSettingBtn.setOnClickListener {
//            startActivity(Intent(this@MyGroupActivity, ???::class.java))
//        }

        // 첫 번째 그룹원 클릭 -> 캘린더로 넘어감 (???에 넘어갈 액티비티 이름 적기)
//        mBinding.grpMemBtn1.setOnClickListener {
//            startActivity(Intent(this@MyGroupActivity, ???::class.java))
//        }

        // 두 번째 그룹원 클릭 -> 캘린더로 넘어감 (???에 넘어갈 액티비티 이름 적기)
//        mBinding.grpMemBtn2.setOnClickListener {
//            startActivity(Intent(this@MyGroupActivity, ???::class.java))
//        }

        // 세 번째 그룹원 클릭 -> 캘린더로 넘어감 (???에 넘어갈 액티비티 이름 적기)
//        mBinding.grpMemBtn3.setOnClickListener {
//            startActivity(Intent(this@MyGroupActivity, ???::class.java))
//        }

        // 네 번째 그룹원 클릭 -> 캘린더로 넘어감 (???에 넘어갈 액티비티 이름 적기)
//        mBinding.grpMemBtn4.setOnClickListener {
//            startActivity(Intent(this@MyGroupActivity, ???::class.java))
//        }

    }
}