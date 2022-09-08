// 캘린더 - 음식추가 - 상세 2

package com.example.f4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f4.databinding.FragmentAddFoodDialog2Binding



class AddFoodDialog2Fragment : DialogFragment() {

    private lateinit var binding: FragmentAddFoodDialog2Binding

    val nutrientList = arrayListOf<AddFoodDialData>(
        AddFoodDialData("열량", "1kcal"),
        AddFoodDialData("탄수화물", "2kcal"),
        AddFoodDialData("단백질", "3kcal"),
        AddFoodDialData("지방", "4kcal"),
        AddFoodDialData("콜레스테롤", "5kcal"),
        AddFoodDialData("식이섬유", "6kcal"),
        AddFoodDialData("칼륨", "7kcal")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 알림창이 띄워져있는 동안 배경 클릭 불가능
        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFoodDialog2Binding.inflate(inflater, container, false)

        // AddFoodDialogAdapter와 연결
        val adapter = AddFoodDialogAdapter(nutrientList)
        binding.FoodDialRv.adapter = adapter

        // x 버튼 클릭 시 이전 프래그먼트로 돌아감
        binding.addFoodDial2Cancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}