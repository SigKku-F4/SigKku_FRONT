package com.example.f4.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Foodlist
import com.example.f4.view.AddFoodDialogFragment
import java.lang.Math.round
import kotlin.math.roundToInt

class AddFoodListAdapter (
    val context: Context?, val foodList: List<Foodlist>?, val date: String?,
    fragmentManager: FragmentManager
) : RecyclerView.Adapter<AddFoodListAdapter.ItemViewHolder>() {
    // adapter에서는 supportFragmentManager가 없음
    // 따라서 FragmentManager를 인자로 받아와야 함
    // 생성자로 해당 클래스에서 사용할 수 있도록 변수화
    private var mFragmentManager: FragmentManager

    init {
        mFragmentManager = fragmentManager
    }

    var TAG = "AddFoodListAdapter"

    var mPosition = 0
    var foodListId: Long = 0
    //var date: String = ""

    private fun setPosition(position:Int){
        mPosition = position
    }

    inner class ItemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val foodName = itemView.findViewById<TextView>(R.id.foodName)
        val foodAmount = itemView.findViewById<TextView>(R.id.oneSupplyAmount)
        val foodKcal = itemView.findViewById<TextView>(R.id.kcal)

        // 리스트 클릭 시 AddFoodDialog 나타남
        fun bind(fragmentManager: FragmentManager, foodId: Long) {
            itemView.setOnClickListener {
                val foodDial = AddFoodDialogFragment()
                val bundle = Bundle()

                bundle.putString("date", date)
                Log.d("데이트 adapter", "${date}")
                bundle.putString("foodIdToDial", foodId.toString())
                foodDial.arguments = bundle

                foodDial.show(fragmentManager, foodDial.tag)
            }
        }
    }

    // 아이템 하나가 들어갈 뷰를 만들고 뷰 홀더에 넣어줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.add_food_lv_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    // 뷰를 그리는 부분
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Foodlist = foodList!!.get(position)

        var kcal = (foodList?.get(position)?.kcal*100).roundToInt()/100f
        foodListId = foodList?.get(position)?.foodListId

        holder.foodName.setText(foodList?.get(position)?.foodName.toString())
        holder.foodAmount.setText(foodList?.get(position)?.oneSupplyAmount.toString())
        holder.foodKcal.setText(kcal.toString())

        holder.bind(mFragmentManager, foodListId)
    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (foodList != null) {
            size = foodList.size
        }
        return size
    }

}