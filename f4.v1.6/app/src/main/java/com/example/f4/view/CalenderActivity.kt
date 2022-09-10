package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.OnItemListener
import com.example.f4.adapter.CalendarAdapter
import com.example.f4.databinding.ActivityCalenderBinding
import kotlinx.android.synthetic.main.activity_calender.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

//, OnItemListener
class CalenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalenderBinding

    //년월 변수
    lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        Calender_Calender.setOnClickListener {
            var cc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(cc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Spoon.setOnClickListener {
            var csp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(csp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Group.setOnClickListener {
            var cg_intent= Intent(this,GroupActivity::class.java)
            startActivity(cg_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        Calender_Setting.setOnClickListener {
            var cs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(cs_intent)
            overridePendingTransition(0, 0);
            finish()

        }

//        //binding 초기화
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender)
//
//        //현재 날짜
//        selectedDate = LocalDate.now()
//
//        //화면 설정
//        setMonthView()
//
//        //이전달 버튼 이벤트
//        binding.preBtn.setOnClickListener{
//            // 현재 월 -1 변수에 담기
//            selectedDate =selectedDate.minusMonths(1)
//            setMonthView()
//        }
//
//        //다음 달 버튼 이벤트
//        binding.nextBtn.setOnClickListener{
//            selectedDate = selectedDate.plusMonths(1)
//            setMonthView()
//        }

    }

//    private fun setMonthView(){
//        //년월 텍스트뷰 셋팅
//        binding.monthYearText.text = YearMonthFromDate(selectedDate)
//
//        //날짜 생성해서 리스트에 담기
//        val dayList = dayInMonthArray(selectedDate)
//
//        //어댑터 초기화
//        val adapter = CalendarAdapter(dayList, this, YearMonthFromDate(selectedDate))
//
//        //레이아웃 설정(열 7개)
//        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
//
//        //레이아웃 적용
//        binding.recyclerView.layoutManager = manager
//
//        //어댑터 적용
//        binding.recyclerView.adapter = adapter
//
//    }
//
//    //날짜 타입 설정 (월, 년)
//    private fun monthYearFromDate(date: LocalDate):String{
//        var formatter = DateTimeFormatter.ofPattern("MM.yyyy")
//
//        //받아온 날짜를 해당 포맷으로 변경
//        return date.format(formatter)
//    }
//
//    //날짜 타입 (년, 월)
//    private fun YearMonthFromDate(date: LocalDate):String{
//        var formatter = DateTimeFormatter.ofPattern("yyyy.MM")
//
//        //받아온 날짜를 해당 포맷으로 변경
//        return date.format(formatter)
//    }
//
//    //날짜 생성
//
//    private fun dayInMonthArray(date: LocalDate): ArrayList<String>{
//        var dayList = ArrayList<String>()
//
//        var yearMonth = YearMonth.from(date)
//
//        //해당 월 마지막 날짜 가져오기(예: 28, 29, 30, 31)
//        var lastDay = yearMonth.lengthOfMonth()
//
//        //해당 월의 첫 번째 날 가져오기(예: 4월 1일)
//        var firstDay= selectedDate.withDayOfMonth(1)
//
//        //첫 번째 날 요일 가져오기 (월: 1, 일: 7)
//        var dayofWeek = firstDay.dayOfWeek.value
//
//        for(i in 1..41){
//            if (i <= dayofWeek || i >(lastDay + dayofWeek)){
//                dayList.add("")
//
//            }else{
//                //            dayList.add((i-dayofWeek).toString())
//                //            년월도 함께 리스트에 추가
//                dayList.add( YearMonthFromDate(selectedDate) + "." +(i-dayofWeek).toString())
//
//            }
//        }
//
//        return dayList
//    }
//
//    //아이템 클릭 이벤트
//    override fun onItemClick(dayText: String) {
//
//        var yearMonthDay =  dayText
//
//        val intent = Intent(this,DietDayActivity::class.java)
//        intent.putExtra("selectDate", yearMonthDay)
//        startActivity(intent)
//
////        Toast.makeText(this, yearMonthDay, Toast.LENGTH_SHORT).show()
//    }


}