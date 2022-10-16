package com.example.f4.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Foodlists
import com.example.f4.view.AddFoodDialog2Fragment
import kotlin.math.roundToInt

class Spoon2ListAdapter(
    val context: Context?, val recFoodList: List<Foodlists>?,
    fragmentManager: FragmentManager
) : RecyclerView.Adapter<Spoon2ListAdapter.ItemViewHolder>() {
    // adapter에서는 supportFragmentManager가 없음
    // 따라서 FragmentManager를 인자로 받아와야 함
    // 생성자로 해당 클래스에서 사용할 수 있도록 변수화
    private var mFragmentManager: FragmentManager

    init {
        mFragmentManager = fragmentManager
    }

    var TAG = "Spoon2ListAdapter"

    var mPosition = 0

    private fun setPosition(position:Int){
        mPosition = position
    }

    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var recFoodName = itemView.findViewById<TextView>(R.id.recFoodName)
        var recFoodKcal = itemView.findViewById<TextView>(R.id.recFoodKcal)
        var recCar = itemView.findViewById<TextView>(R.id.recCar)
        var recPro = itemView.findViewById<TextView>(R.id.recPro)
        var recFat = itemView.findViewById<TextView>(R.id.recFat)
        var recCategory = itemView.findViewById<ImageView>(R.id.recFoodIv)

        // 리스트 클릭 시 AddFood 상세Dialog 나타남
        fun bind(fragmentManager: FragmentManager, foodName: String, kcal: Int, amount: Float, car: Float, pro: Float,
                 fat: Float, pho: Float, die: Float, moi: Float, vit: Float, fol: Float, clc: Float, sod: Float,
                 pot: Float, mag: Float) {
            itemView.setOnClickListener {
                val foodDial = AddFoodDialog2Fragment()
                val bundle = Bundle()

                // AddFoodDialog2Fragment에게 전달
                bundle.putString("foodNameToDial2", foodName)
                bundle.putString("foodKcalToDial2", kcal.toString())
                bundle.putString("foodAmountToDial2", amount.toString())
                bundle.putString("foodCarToDial2", car.toString())
                bundle.putString("foodProToDial2", pro.toString())
                bundle.putString("foodFatToDial2", fat.toString())
                bundle.putString("foodPhoToDial2", pho.toString())
                bundle.putString("foodDieToDial2", die.toString())
                bundle.putString("foodMoiToDial2", moi.toString())
                bundle.putString("foodVitToDial2", vit.toString())
                bundle.putString("foodFolToDial2", fol.toString())
                bundle.putString("foodClcToDial2", clc.toString())
                bundle.putString("foodSodToDial2", sod.toString())
                bundle.putString("foodPotToDial2", pot.toString())
                bundle.putString("foodMagToDial2", mag.toString())

                foodDial.arguments = bundle
                foodDial.show(fragmentManager, foodDial.tag)
            }
        }
    }

    // 아이템 하나가 들어갈 뷰를 만들고 뷰 홀더에 넣어줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spoon2_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    // 뷰를 그리는 부분
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Foodlists = recFoodList!!.get(position)

        var name = recFoodList?.get(position)?.foodName
        var kcal = (recFoodList?.get(position)?.nutrient.calorie).roundToInt()
        var amount = (recFoodList?.get(position)?.oneSupplyAmount*100).roundToInt()/100f
        var car = (recFoodList?.get(position)?.nutrient.carbohydrate*100).roundToInt()/100f
        var pro = (recFoodList?.get(position)?.nutrient.protein*100).roundToInt()/100f
        var fat = (recFoodList?.get(position)?.nutrient.fat*100).roundToInt()/100f
        var pho = (recFoodList?.get(position)?.nutrient.phosphorus*100).roundToInt()/100f
        var die = (recFoodList?.get(position)?.nutrient.dietaryFiber*100).roundToInt()/100f
        var moi = (recFoodList?.get(position)?.nutrient.moisutre*100).roundToInt()/100f
        var vit = (recFoodList?.get(position)?.nutrient.vitaminB12*100).roundToInt()/100f
        var fol = (recFoodList?.get(position)?.nutrient.folicAcid*100).roundToInt()/100f
        var clc = (recFoodList?.get(position)?.nutrient.calcium*100).roundToInt()/100f
        var sod = (recFoodList?.get(position)?.nutrient.sodium*100).roundToInt()/100f
        var pot = (recFoodList?.get(position)?.nutrient.potassium*100).roundToInt()/100f
        var mag = (recFoodList?.get(position)?.nutrient.magnesium*100).roundToInt()/100f

        var category = (recFoodList?.get(position)?.category.toString())

        if (category == "곡류 및 서류")
            holder.recCategory.setImageResource(R.drawable.category1)
        else if (category == "과자류")
            holder.recCategory.setImageResource(R.drawable.category2)
        else if (category == "구이류")
            holder.recCategory.setImageResource(R.drawable.category3)
        else if (category == "국 및 탕류")
            holder.recCategory.setImageResource(R.drawable.category4)
        else if (category == "기타")
            holder.recCategory.setImageResource(R.drawable.category5)
        else if (category == "김치류")
            holder.recCategory.setImageResource(R.drawable.category6)
        else if (category == "면 및 만두류")
            holder.recCategory.setImageResource(R.drawable.category7)
        else if (category == "밥류")
            holder.recCategory.setImageResource(R.drawable.category8)
        else if (category == "볶음류")
            holder.recCategory.setImageResource(R.drawable.category9)
        else if (category == "빵류")
            holder.recCategory.setImageResource(R.drawable.category10)
        else if (category == "생채 및 무침류")
            holder.recCategory.setImageResource(R.drawable.category11)
        else if (category == "숙채류")
            holder.recCategory.setImageResource(R.drawable.category12)
        else if (category == "아이스크림류")
            holder.recCategory.setImageResource(R.drawable.category13)
        else if (category == "음료 및 차류")
            holder.recCategory.setImageResource(R.drawable.category14)
        else if (category == "장아찌 및 절임류")
            holder.recCategory.setImageResource(R.drawable.category15)
        else if (category == "전.적 및 부침류")
            holder.recCategory.setImageResource(R.drawable.category16)
        else if (category == "젓갈류")
            holder.recCategory.setImageResource(R.drawable.category17)
        else if (category == "조림류")
            holder.recCategory.setImageResource(R.drawable.category18)
        else if (category == "죽 및 스프류")
            holder.recCategory.setImageResource(R.drawable.category19)
        else if (category == "찌개 및 전골류")
            holder.recCategory.setImageResource(R.drawable.category20)
        else if (category == "찜류")
            holder.recCategory.setImageResource(R.drawable.category21)
        else if (category == "튀김류")
            holder.recCategory.setImageResource(R.drawable.category22)
        else if (category == "포류")
            holder.recCategory.setImageResource(R.drawable.category23)
        else //회류
            holder.recCategory.setImageResource(R.drawable.category24)


        holder.recFoodName.setText(name)
        holder.recFoodKcal.setText(kcal.toString())
        holder.recCar.setText(car.toString())
        holder.recPro.setText(pro.toString())
        holder.recFat.setText(fat.toString())

        val foodListId = recFoodList?.get(position)?.foodListId
        holder.bind(mFragmentManager, name, kcal, amount, car, pro, fat, pho, die, moi, vit, fol, clc, sod, pot, mag)
    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (recFoodList != null) {
            size = recFoodList.size
        }
        return size
    }

}