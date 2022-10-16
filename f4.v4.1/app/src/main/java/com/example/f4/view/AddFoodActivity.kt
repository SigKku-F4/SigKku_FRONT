package com.example.f4.view

// 캘린더 - 음식 추가

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.adapter.AddFoodListAdapter
import com.example.f4.data.Addfood
import com.example.f4.data.Foodlist
import com.example.f4.databinding.ActivityAddFoodBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_add_food.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFoodActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityAddFoodBinding

    private lateinit var foodListView: RecyclerView
    lateinit var addFoodAdapter: AddFoodListAdapter

    var data: String = ""
    var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        var getDate = intent.getStringExtra("date").toString()
        date = getDate
        Log.d("데이트 activity", "${date}")
//
//        val foodDial = AddFoodDialogFragment()
//        val bundle = Bundle()
//
//        bundle.putString("date", date)
//        foodDial.arguments = bundle

        mBinding.BackAddFood.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }

        foodListView = findViewById(R.id.foodListView)

        // editText 엔터 클릭 이벤트
        mBinding.searchFood.setOnKeyListener {v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                data = searchFood.text.toString()
                loadFoodLists(data)
                handled = true
            }

            handled

        }

    }

    // 통신 함수
    private fun loadFoodLists(name: String) {
        var query: HashMap<String, Int> = hashMapOf("page" to 0, "size" to 30)

        (application as MasterApplication).service.addFoods(
            Token.token, name, query.get("page")!!, query.get("size")!!
        ).enqueue(object :
            Callback<Addfood>
        {
            override fun onFailure(call: Call<Addfood>, t: Throwable) {
                //통신 실패
                Log.d("TEST fail : ", "음식추가 통신 실패")
            }
            override fun onResponse(call: Call<Addfood>, response: Response<Addfood>) {

                //통신, 응답 성공

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        val data = response.body()?.foodList

                        // adapter에 데이터 넣기
                        if (data != null) {
                            setAdapter(data, date)
                        }
                    }

                val total_elements = response.body()?.totalElements.toString()
                val page_number = response.body()?.pageNumber.toString()
                val food_list = response.body()?.foodList

                Log.d("통신 성공 : ", "totalElements = ${total_elements}")
                Log.d("통신 성공 : ", "pageNumber = ${page_number}")
                Log.d("통신 성공 : ", "foodList = ${food_list}")

            }
        })
    }

    private fun setAdapter(foodList: List<Foodlist>, date: String) {
        addFoodAdapter = AddFoodListAdapter(this, foodList, date, supportFragmentManager)
        foodListView.adapter = addFoodAdapter
        foodListView.layoutManager = LinearLayoutManager(this)
    }



}