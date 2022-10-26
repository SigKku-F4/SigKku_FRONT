package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.Spoon2ListAdapter
import com.example.f4.data.Foodlists
import com.example.f4.data.Recommendedsearch
import com.example.f4.databinding.ActivitySpoon2Binding
import kotlinx.android.synthetic.main.activity_spoon2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class Spoon2Activity : AppCompatActivity() {

    private lateinit var mBinding : ActivitySpoon2Binding
    var query: HashMap<String, Int> = hashMapOf("page" to 0, "size" to 30)
    val date: LocalDate = LocalDate.now()

    private lateinit var recFoodLv: RecyclerView
    lateinit var recommendedFoodAdapter: Spoon2ListAdapter

    var data: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySpoon2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        recFoodLv = findViewById(R.id.recFoodLv)

        mBinding.SpoonEt.setOnKeyListener {v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                data = SpoonEt.text.toString()
                communication(data)
                handled = true
            }

            handled

        }

        mBinding.BackSpoon2.setOnClickListener {
            var spoon_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(spoon_intent)
            overridePendingTransition(0, 0);
            finish()
        }

        Spoon2_Calender.setOnClickListener {
            var spc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(spc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon2_Spoon.setOnClickListener {
            var spsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(spsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon2_Group.setOnClickListener {
            var spg_intent: Intent = Intent(this,GroupActivity::class.java)
            startActivity(spg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Spoon2_Setting.setOnClickListener {
            var sps_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(sps_intent)
            overridePendingTransition(0, 0);
            finish()

        }
    }

    private fun communication(name: String) {
        (application as MasterApplication).service.recommendFoodsSearch(
            Token.token, date.toString(), name, query.get("page")!!, query.get("size")!!
        ).enqueue(object :
            Callback<Recommendedsearch>
        {
            override fun onFailure(call: Call<Recommendedsearch>, t: Throwable) {
            }
            override fun onResponse(call: Call<Recommendedsearch>, response: Response<Recommendedsearch>) {

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        val data = response.body()?.foodLists
                        if (data != null) {
                            setAdapter(data)
                        }
                    }
            }
        })
    }

    private fun setAdapter(foodLists: List<Foodlists>) {
        recommendedFoodAdapter = Spoon2ListAdapter(this, foodLists, supportFragmentManager)
        recFoodLv.adapter = recommendedFoodAdapter
        recFoodLv.layoutManager = LinearLayoutManager(this)
    }

}
