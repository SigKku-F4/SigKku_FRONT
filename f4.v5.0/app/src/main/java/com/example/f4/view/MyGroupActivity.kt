package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.MyGroupListAdapter
import com.example.f4.data.Mygroup
import com.example.f4.databinding.ActivityMyGroupBinding
import kotlinx.android.synthetic.main.activity_my_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyGroupActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMyGroupBinding
    var id: Long = 0
    var isOwner: Boolean = false
    var groupCurrent: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if(intent.hasExtra("groupId")) {
            id = intent.getLongExtra("groupId", 0)
        }

        if(intent.hasExtra("groupCur")) {
            groupCurrent = intent.getIntExtra("groupCur", 0)
            grpMemNum.text = groupCurrent.toString()
        }

        loadMyGroup()
        mBinding.BackMyGroup.setOnClickListener {
            finish()
        }

        mBinding.grSettingBtn.setOnClickListener {
            val intent_L = Intent(this@MyGroupActivity, GroupSetting_L::class.java) // 그룹장
            val intent_M = Intent(this@MyGroupActivity, GroupSetting_M::class.java) // 그룹원
            intent_L.putExtra("groupId", id) // groupId
            intent_M.putExtra("groupId", id) // groupId
            intent_L.putExtra("groupCur",groupCurrent)
            intent_M.putExtra("groupCur",groupCurrent)
            if (isOwner == false) {
                startActivity(intent_M)
            } else {
                startActivity(intent_L)
            }

            finish()
        }

        My_Group_Calender.setOnClickListener {
            var mgc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(mgc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        My_Group_Spoon.setOnClickListener {
            var mgsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(mgsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        My_Group_Group.setOnClickListener {
            var mgg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(mgg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        My_Group_Setting.setOnClickListener {
            var mgs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(mgs_intent)
            overridePendingTransition(0, 0);
            finish()

        }
    }

    private fun setAdapter(data: Mygroup) {
        val mAdapter = MyGroupListAdapter(this, data)
        rvMemList.adapter = mAdapter
        rvMemList.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    private fun loadMyGroup() {

        (application as MasterApplication).service.myGroups(Token.token, id).enqueue(object : Callback<Mygroup>
        {
            override fun onFailure(call: Call<Mygroup>, t: Throwable) {
            }
            override fun onResponse(call: Call<Mygroup>, response: Response<Mygroup>) {


                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        val data = response.body()

                        if (data != null) {
                            isOwner = data.isOwner
                            setAdapter(data)
                        }

                    }

                val group_name = response.body()?.groupName
                val group_description = response.body()?.description

                mBinding.groupName.setText("${group_name}")
                mBinding.description.setText("${group_description}")

            }
        })
    }
}