package com.example.f4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.data.Calendar
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(private val dayList: ArrayList<String>, private val groupList: List<Calendar>? ,private val onItemListener: OnItemListener, private val yearAndMonth: String):RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dayText: TextView= itemView.findViewById(R.id.dayText)
        val stampGood: ImageView= itemView.findViewById(R.id.stampGoodImg)
        val stampSoso: ImageView= itemView.findViewById(R.id.stampSosoImg)
        val stampBad: ImageView= itemView.findViewById(R.id.stampBadImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_calendar_item, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        var day = dayList[holder.adapterPosition]
        var date = dayList[holder.adapterPosition]

        date = date.replace(".","-")
        holder.dayText.text = day.replace(yearAndMonth+".","")

        for (i in 0..groupList?.size!!-1){
            if(groupList != null){
                if (groupList?.get(i)?.dateTime== date) {
                    if (groupList?.get(i)?.stamp == "GREEN") {
                        holder.stampGood.visibility = View.VISIBLE
                    }
                    //"YELLOW" 인지 if문
                    else if (groupList?.get(i)?.stamp == "YELLOW") {
                        holder.stampSoso.visibility = View.VISIBLE
                    }
                    //"RED"인지 if문
                    else if (groupList?.get(i)?.stamp == "RED") {
                        holder.stampBad.visibility = View.VISIBLE
                    }
                }
            }
        }

        holder.itemView.setOnClickListener{
            onItemListener.onItemClick(day)

        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }



}