package com.example.f4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.data.Food
import com.example.f4.databinding.ActivityDietFoodItemBinding
import kotlinx.android.synthetic.main.activity_diet_day_item.view.*

class DietFoodAdapter : RecyclerView.Adapter<DietFoodAdapter.ViewHolder>(){

    private lateinit var items: ArrayList<Food>

    fun build(i: ArrayList<Food>): DietFoodAdapter {
        items = i
        return this
    }


    class ViewHolder(val binding: ActivityDietFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Food) {
            binding.dietFoodName.text = item.name
            binding.dietFoodKcal.text = item.nutrient.calorie.toInt().toString()+"Kcal"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietFoodAdapter.ViewHolder =
        ViewHolder(ActivityDietFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


}