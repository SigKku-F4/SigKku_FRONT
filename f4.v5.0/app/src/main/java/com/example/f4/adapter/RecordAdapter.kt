package com.example.f4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Foods
import com.example.f4.data.Maingroup
import com.example.f4.data.Record_food
import com.example.f4.data.Foodlist as Foodlist

class RecordAdapter(
    val context: Context?, private val recordList: List<Record_food>?
):RecyclerView.Adapter<RecordAdapter.ItemViewHolder>(){

    var mPosition = 0

    private fun setPosition(position:Int){
        mPosition = position
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val add_food = itemView.findViewById<TextView>(R.id.add_food)
        val add_gram = itemView.findViewById<TextView>(R.id.add_gram)
        val add_kcal = itemView.findViewById<TextView>(R.id.add_kcal)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_record, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.add_food.setText(recordList?.get(position)?.foodName)
        holder.add_gram.setText(recordList?.get(position)?.amount.toString())
        holder.add_kcal.setText(recordList?.get(position)?.kcal.toString())
    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

}