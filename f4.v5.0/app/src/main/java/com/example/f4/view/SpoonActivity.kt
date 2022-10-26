package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.data.Recommendedfood
import com.example.f4.databinding.ActivitySpoonBinding
import kotlinx.android.synthetic.main.activity_spoon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class SpoonActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivitySpoonBinding
    var date: LocalDate = LocalDate.now()
    var query: HashMap<String, Int> = hashMapOf("page" to 0, "size" to 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySpoonBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.spoonFragmentContainer, Spoon1Fragment())
        transation.commit()

        change_btn1.setOnClickListener(this)
        change_btn2.setOnClickListener(this)

        loadComments()


        searchSpoon.setOnClickListener {
            var spoon2_intent: Intent = Intent(this, Spoon2Activity::class.java)
            startActivity(spoon2_intent)
            overridePendingTransition(0, 0);
            finish()
        }

        Spoon_Calender.setOnClickListener {
            var spc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(spc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon_Spoon.setOnClickListener {
            var spsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(spsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon_Group.setOnClickListener {
            var spg_intent: Intent = Intent(this, GroupActivity::class.java)
            startActivity(spg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon_Setting.setOnClickListener {
            var sps_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(sps_intent)
            overridePendingTransition(0, 0);
            finish()

        }
    }

    override fun onClick(v: View) {
        val transaction = supportFragmentManager.beginTransaction()
        when (v.id) {
            R.id.change_btn1->{
                transaction.replace(R.id.spoonFragmentContainer,Spoon1Fragment())
                transaction.commit()
            }
            R.id.change_btn2 -> {
                transaction.replace(R.id.spoonFragmentContainer,Spoon2Fragment())
                transaction.commit()
            }
        }
    }

    private fun loadComments() {
        (application as MasterApplication).service.recommendFoods(
            Token.token, date.toString(), query.get("page")!!, query.get("size")!!
        ).enqueue(object :
            Callback<Recommendedfood>
        {
            override fun onFailure(call: Call<Recommendedfood>, t: Throwable) {
            }
            override fun onResponse(call: Call<Recommendedfood>, response: Response<Recommendedfood>) {
                if (response.isSuccessful.not()) {
                    return
                }

                val spoonData = response.body()
                if (spoonData != null) {

                    val todayComment = spoonData?.recommendComments
                    mBinding.todayRec.setText("${todayComment}")
                }
            }
        })
    }



}