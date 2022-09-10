package com.example.f4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.OnItemListener
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(private val dayList: ArrayList<String>, private val onItemListener: OnItemListener, private val yearAndMonth: String):RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

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


        //yearAndMonth.replace(".", "-")
        holder.dayText.text = day.replace(yearAndMonth+".","")


        //로그로 확인
//      Log.d("log",day)

        //년, 월 비교해서 표시해야됨~!!
        //data를 받아와서 "GREEN" "YELLOW" "BAD" 인지 확인 후 VISIBLE 해야됨
        //day : String 일, stamp : String 도장 색.   일, 도장색 비교 후에 넣어주기
        //도장이 있는 지 확인 -> 도장색이 무엇인지 확인 -> day확인 후 도장 찍기

        //"GREEN"  인지 if문
        if(day == "2022.09.7" || day == "2022.10.14" || day=="2022.10.11"){
            holder.stampGood.visibility=View.VISIBLE
        }

        //"YELLOW" 인지 if문
        if(day == "2022.09.16"){
            holder.stampSoso.visibility=View.VISIBLE
        }

        //"RED"인지 if문
        if(day =="2022.08.12" || day=="2022.09.10"){
            holder.stampBad.visibility=View.VISIBLE
        }


        //텍스트 색상 지정(토, 일)
//        if ((position +1)%7 == 0){ //토요일은 파랑
//            holder.stampGood.visibility=View.VISIBLE
//           holder.dayText.setTextColor(Color.BLACK)
//
//        }
//        if (position == 0 || position%7 == 0){ //일요일은 빨강
//            holder.dayText.setTextColor()
//        }

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

