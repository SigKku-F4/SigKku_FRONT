package com.example.f4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f4.databinding.ActivityGroupMainBinding

class GroupMainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityGroupMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGroupMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 그룹 클릭 시 상세 그룹화면으로 넘어감
        mBinding.grLinearLayout.setOnClickListener {
            startActivity(Intent(this@GroupMainActivity, MyGroupActivity::class.java))
        }

        // 그룹 생성 및 가입 다이얼로그 화면 뜰 때
        clickViewEvents()

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