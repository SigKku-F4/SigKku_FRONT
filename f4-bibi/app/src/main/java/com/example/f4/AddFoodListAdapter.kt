package com.example.f4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AddFoodListAdapter (val context: Context, val foodList: ArrayList<AddFoodData>) : BaseAdapter() {

    // xml 파일의 View와 데이터를 연결하는 핵심 역할을 하는 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다.
        val view: View = LayoutInflater.from(context).inflate(R.layout.add_food_lv_item, null)

        // 위에서 생성된 view를 res-layout-add_food_lv_item.xml 파일의 각 View와 연결하는 과정.
        val foodName = view.findViewById<TextView>(R.id.foodName)
        val foodAmount = view.findViewById<TextView>(R.id.oneSupplyAmount)
        val foodKcal = view.findViewById<TextView>(R.id.kcal)
        val foodPlus = view.findViewById<ImageView>(R.id.foodPlus)

        // ArrayList<AddFoodData>의 변수 food의 이미지와 데이터를 ImageView와 TextView에 담는다.
        val food = foodList[position]
        val resourceId = context.resources.getIdentifier(food.plus, "drawable", context.packageName)
        foodName.text = food.name
        foodAmount.text = food.amount
        foodKcal.text = food.kcal
        foodPlus.setImageResource(resourceId)

        return view
    }

    // ListView에 속한 item의 전체 수를 반환
    override fun getCount(): Int {
        return foodList.size
    }

    // 해당 위치의 item을 반환
    override fun getItem(position: Int): Any {
        return foodList[position]
    }

    // 해당 위치의 item id를 반환
    override fun getItemId(position: Int): Long {
        return 0
    }

}