package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.data.Maingroup
import com.example.f4.data.Recommendedfood
import com.example.f4.data.Recommendedfoodmy
import com.example.f4.databinding.ActivitySpoonBinding
import kotlinx.android.synthetic.main.activity_spoon.*
import kotlinx.android.synthetic.main.activity_spoon.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.math.roundToInt

class SpoonActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivitySpoonBinding
    var date: LocalDate = LocalDate.now() // 항상 오늘 날짜로 받아오기
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

        //통신 - Comment 받아오기
        loadComments()

        // 추천음식 검색 화면으로 이동

        searchSpoon.setOnClickListener {
            var spoon2_intent: Intent = Intent(this, Spoon2Activity::class.java)
            startActivity(spoon2_intent)
            overridePendingTransition(0, 0);
            finish()
        }

        // 하단 바

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
                //transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.change_btn2 -> {
                transaction.replace(R.id.spoonFragmentContainer,Spoon2Fragment())
                //transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    // 통신 함수 - 섭취하지 않은 음식 포함 추천
    private fun loadComments() {
        (application as MasterApplication).service.recommendFoods(
            Token.token, date.toString(), query.get("page")!!, query.get("size")!!
        ).enqueue(object :
            Callback<Recommendedfood>
        {
            override fun onFailure(call: Call<Recommendedfood>, t: Throwable) {
                //통신 실패
                Log.d("(N)TEST fail : ", "추천음식 통신 실패")
            }
            override fun onResponse(call: Call<Recommendedfood>, response: Response<Recommendedfood>) {
                if (response.isSuccessful.not()) {
                    //응답 실패
                    Log.d("(N)TEST fail : ", "추천음식 응답 실패")
                    return
                }
                //통신, 응답 성공

                val spoonData = response.body()
                if (spoonData != null) {
                    Log.d("통신성공!! TEST :", "${spoonData.recommendComments}")

                    val todayComment = spoonData?.recommendComments
                    mBinding.todayRec.setText("${todayComment}")
                }
            }
        })
    }



}