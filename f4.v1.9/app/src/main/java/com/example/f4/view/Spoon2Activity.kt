package com.example.f4.view

// 추천음식 첫 화면

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.adapter.Spoon2ListAdapter
import com.example.f4.databinding.ActivitySpoon2Binding
import kotlinx.android.synthetic.main.activity_spoon2.*

class Spoon2Activity : AppCompatActivity() {

    private lateinit var mBinding : ActivitySpoon2Binding

    private lateinit var recFoodLv: RecyclerView
    lateinit var recommendedFoodListAdapter: Spoon2ListAdapter
    lateinit var fooddata: ArrayList<Spoon2Data>

    lateinit var searchViewRecFood: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_spoon2)

        mBinding = ActivitySpoon2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        recFoodLv = findViewById(R.id.recFoodLv)
        searchViewRecFood = findViewById(R.id.searchViewRecFood)

        searchViewRecFood.setOnQueryTextListener(searchViewTextListener)

        fooddata = tempPersons()
        setAdapter()

        mBinding.BackSpoon2.setOnClickListener {
            var spoon_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(spoon_intent)
            overridePendingTransition(0, 0);
            finish()
        }

        // 하단 바

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

    //SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                recommendedFoodListAdapter.filter.filter(s)
                Log.d(ContentValues.TAG, "SearchVies Text is changed : $s")
                return false
            }

        }

    fun setAdapter(){
        //리사이클러뷰에 리사이클러뷰 어댑터 부착
        recFoodLv.layoutManager = LinearLayoutManager(this)
        recommendedFoodListAdapter = Spoon2ListAdapter(this, fooddata)
        recFoodLv.adapter = recommendedFoodListAdapter
    }

    fun tempPersons(): ArrayList<Spoon2Data> {
        var tempPersons = ArrayList<Spoon2Data>()
        tempPersons.add(Spoon2Data("순찌", 123, 1, 2, 3))
        tempPersons.add(Spoon2Data("부찌", 123, 1, 2, 3))
        tempPersons.add(Spoon2Data("된찌", 123, 1, 2, 3))
        tempPersons.add(Spoon2Data("청찌", 123, 1, 2, 3))
        tempPersons.add(Spoon2Data("김찌", 123, 1, 2, 3))
        return tempPersons
    }


}