package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f4.adapter.GroupListAdapter
import com.example.f4.databinding.ActivityGroupBinding
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityGroupBinding

    var members = "소현" + "  " + "비비" + "  " + "동민" + "  " + "유림"

    var groupList = arrayListOf<GroupData>(
        GroupData("F1", 1, members),
        GroupData("F2", 2, members),
        GroupData("F3", 3, members),
        GroupData("F4", 4, members)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_group)

        mBinding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // GroupListAdapter와 연결
        val groupAdapter = GroupListAdapter(this, groupList)
        rvGrList.adapter = groupAdapter
        
        // setHasFixedSize true로 설정
        val lm = LinearLayoutManager(this)
        rvGrList.layoutManager = lm
        rvGrList.setHasFixedSize(true)

        // 그룹 생성 및 가입 다이얼로그 화면 뜰 때
        clickViewEvents()

        // 하단 바

        Group_Calender.setOnClickListener {
            var gc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(gc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Spoon.setOnClickListener {
            var gsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(gsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Group.setOnClickListener {
            var gg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(gg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Group_Setting.setOnClickListener {
            var gs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(gs_intent)
            overridePendingTransition(0, 0);
            finish()

        }

    }

    // 뷰 클릭 이벤트 정의
    private fun clickViewEvents() {
        // 하단 plus 버튼 클릭
        mBinding.grDialBtn.setOnClickListener {
            val dialog = GroupDialog()
            // 알림창이 띄워져있는 동안 배경 클릭 막기 X
            dialog.isCancelable = true
            dialog.show(this.supportFragmentManager, "GroupDialog")
        }

    }

}