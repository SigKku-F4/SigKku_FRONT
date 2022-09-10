package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.adapter.MyGroupListAdapter
import com.example.f4.databinding.ActivityMyGroupBinding
import kotlinx.android.synthetic.main.activity_my_group.*

class MyGroupActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMyGroupBinding

    var groupList = arrayListOf<MyGroupData>(
        MyGroupData("김동민민", "grp_mem_1", 3, 1, 0),
        MyGroupData("박소현현", "grp_mem_2", 2, 1, 1),
        MyGroupData("서유림림", "grp_mem_3", 1, 0, 2),
        MyGroupData("정사비비", "grp_mem_4", 1, 0, 3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // MyGroupListAdapter와 연결
        val groupAdapter = MyGroupListAdapter(this, groupList)
        rvMemList.adapter = groupAdapter

        // 백버튼 클릭 시 그룹 메인화면으로 이동
        mBinding.BackMyGroup.setOnClickListener {
            finish()
        }

        // 그룹 설정 버튼 클릭 시 설정화면으로 넘어감
        mBinding.grSettingBtn.setOnClickListener {
            startActivity(Intent(this@MyGroupActivity, GroupSetting_L::class.java))
        }

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