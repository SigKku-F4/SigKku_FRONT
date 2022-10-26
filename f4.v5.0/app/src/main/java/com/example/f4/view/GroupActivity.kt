package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.GroupListAdapter
import com.example.f4.data.Maingroup
//import com.example.f4.data.User
import com.example.f4.databinding.ActivityGroupBinding
import kotlinx.android.synthetic.main.activity_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val context = intent.getIntExtra("Context", 0)

        if (context == 1)
            Toast.makeText(this, "그룹이 해체되었습니다.", Toast.LENGTH_SHORT).show()
        else if (context == 2)
            Toast.makeText(this, "그룹을 탈퇴했습니다.", Toast.LENGTH_SHORT).show()

        (application as MasterApplication).service.mainGroups(Token.token).enqueue(object :
            Callback<ArrayList<Maingroup>>
        {
            override fun onFailure(call: Call<ArrayList<Maingroup>>, t: Throwable) {
            }
            override fun onResponse(call: Call<ArrayList<Maingroup>>, response: Response<ArrayList<Maingroup>>) {
                if (response.isSuccessful.not()) {
                    return
                }

                val grouplist = response.body()
                if (grouplist != null) {
                    setAdapter(grouplist)
                }
            }
        })

        clickViewEvents()

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


    private fun clickViewEvents() {
        mBinding.grDialBtn.setOnClickListener {
            val dialog = GroupDialog()
            dialog.isCancelable = true
            dialog.show(this.supportFragmentManager, "GroupDialog")
        }

    }

    private fun setAdapter(groupList: List<Maingroup>) {
        val groupAdapter = GroupListAdapter(this, groupList)
        rvGrList.adapter = groupAdapter
        rvGrList.layoutManager = LinearLayoutManager(this)
    }

}