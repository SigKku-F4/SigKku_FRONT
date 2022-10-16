package com.example.f4.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.data.Meal
import com.example.f4.databinding.ActivityDietDayItemBinding
import com.example.f4.view.ChangeRecordActivity
import kotlinx.android.synthetic.main.activity_diet_day_item.view.*
import java.io.Serializable


class DietDayAdapter(val context: Context?, val dateId : String, val foodId : String): RecyclerView.Adapter<DietDayAdapter.ViewHolder>() {

    var mPosition = 0

    var userId = 0

    private lateinit var items: ArrayList<Meal>

    private fun setPosition(position:Int){
        mPosition = position
    }

    fun build(i: ArrayList<Meal>): DietDayAdapter {
        items = i
        return this
    }

    class ViewHolder(val binding: ActivityDietDayItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meal) {
            with(binding)
            {
                if (item.mealType.toString() == "MORNING"){
                    mealType.text = "아침"
                }else if(item.mealType.toString() == "LUNCH"){
                    mealType.text="점심"
                }else if(item.mealType.toString() == "EVENING"){
                    mealType.text="저녁"
                }else{
                    mealType.text="간식"
                }
                foodsRecycle.apply {
                    adapter = DietFoodAdapter().build(item.foods)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietDayAdapter.ViewHolder =
        ViewHolder(
            ActivityDietDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )

    override fun onBindViewHolder(holder: DietDayAdapter.ViewHolder, position: Int) {
//         Log.d("items", items.toString())

        for (i in 0..items[position].foods.size-1){
            var totalKcal = 0
            Log.d("items",items[position].foods.toString())
            for(j in 0..items?.get(position)?.foods.size-1){
                Log.d("Test", "나는 Kcal position ::" +position+"   "+ items[position].foods[j].nutrient.calorie.toInt())
                totalKcal += items?.get(position).foods[j].nutrient.calorie.toInt()
            }
            holder.itemView.mealTotalKcal.text =totalKcal.toString() +"Kcal"
        }


        holder.itemView.dietFoodImg1.setImageResource(0)
        holder.itemView.dietFoodImg2.setImageResource(0)


        if (!(items[position].imgs[0].isEmpty())){
            Log.d("bitmap1", items[position].imgs[0])
            val uri: Uri = Uri.parse(items[position].imgs[0])
            holder.itemView.dietFoodImg1.setImageURI(uri)
        }


        if (!(items[position].imgs[1].isEmpty())){
            Log.d("bitmap2", items[position].imgs[1])
            val uri: Uri = Uri.parse(items[position].imgs[1])
            holder.itemView.dietFoodImg2.setImageURI(uri)

        }



        holder.itemView.setOnClickListener { view ->
            setPosition(position)
            val intent= Intent(context, ChangeRecordActivity::class.java)
            intent.putExtra("date", dateId) // foodId
            intent.putExtra("meal", items[position] as Serializable)
            context?.startActivity(intent)
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}