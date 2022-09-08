package com.example.f4

// 캘린더 - 음식 추가

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.f4.databinding.ActivityAddFoodBinding
import kotlinx.android.synthetic.main.activity_add_food.*

class AddFoodActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityAddFoodBinding

    var foodList = arrayListOf<AddFoodData>(
        AddFoodData("음식1", "양1", "1kcal", "ic_add_circle"),
        AddFoodData("음식2", "양2", "2kcal", "ic_add_circle"),
        AddFoodData("음식3", "양3", "3kcal", "ic_add_circle"),
        AddFoodData("음식4", "양4", "4kcal", "ic_add_circle"),
        AddFoodData("음식5", "양5", "5kcal", "ic_add_circle"),
        AddFoodData("음식6", "양6", "6kcal", "ic_add_circle"),
        AddFoodData("음식7", "양7", "7kcal", "ic_add_circle"),
        AddFoodData("음식8", "양8", "8kcal", "ic_add_circle"),
        AddFoodData("음식9", "양9", "9kcal", "ic_add_circle"),
        AddFoodData("음식10", "양10", "10kcal", "ic_add_circle"),
        AddFoodData("음식11", "양11", "11kcal", "ic_add_circle"),
        AddFoodData("음식12", "양12", "12kcal", "ic_add_circle"),
        AddFoodData("음식13", "양13", "13kcal", "ic_add_circle")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // AddFoodListAdapter와 연결
        val foodAdapter = AddFoodListAdapter(this, foodList)
        foodListView.adapter = foodAdapter

        // 리스트 클릭 시 상세보기 fragment로 이동
        mBinding.foodListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //val selectItem = parent.getItemAtPosition(position) as AddFoodData
            val dialog = AddFoodDialogFragment()
            // 알림창이 띄워져있는 동안 배경 클릭 불가능
            dialog.isCancelable = false
            dialog.show(this.supportFragmentManager, "AddFoodDialogFragment")
        }

        // 임시 - 음식 양 데이터 임시로 받아서 토스트에 띄워봄!
        val foodKey = intent.getStringExtra("sendFoodAmountData")
        Toast.makeText(this, foodKey, Toast.LENGTH_SHORT).show()

        // 위 토스트에서 나오는 데이터를 텍뷰에서 받아서 띄움.
        // if(intent.hasExtra("sendFoodAmountData")) {
            // 받을텍스트뷰.text = intent.getStringExtra("sendFoodAmountData")
        // }

    }

}