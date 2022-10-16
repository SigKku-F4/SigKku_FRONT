package com.example.f4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.OnItemListener
import com.example.f4.data.Calendar
import com.example.f4.data.Maingroup
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


//calendar 리스트 형태로 데이터 받은 다음 사용하기

class CalendarAdapter(private val dayList: ArrayList<String>, private val groupList: List<Calendar>? ,private val onItemListener: OnItemListener, private val yearAndMonth: String):RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dayText: TextView= itemView.findViewById(R.id.dayText)
        val stampGood: ImageView= itemView.findViewById(R.id.stampGoodImg)
        val stampSoso: ImageView= itemView.findViewById(R.id.stampSosoImg)
        val stampBad: ImageView= itemView.findViewById(R.id.stampBadImg)
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_calendar_item, parent, false)

        return ItemViewHolder(view)
    }


    //데이터 설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 변수에 담기
        var day = dayList[holder.adapterPosition]
        var date = dayList[holder.adapterPosition]

        date = date.replace(".","-")
//        Log.d("Date",date)
        holder.dayText.text = day.replace(yearAndMonth+".","")

        //데이터 들어오면 테스트 할 코드~~!!
//        size 받아내야 됨 -> 어케 하지 dateId로 해보고 싶은데 데이터가 없어서 시도 못 하는중
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




        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener{
            //인터페이스를 통해 날짜를 넘겨준다.
            onItemListener.onItemClick(day)

        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }



}