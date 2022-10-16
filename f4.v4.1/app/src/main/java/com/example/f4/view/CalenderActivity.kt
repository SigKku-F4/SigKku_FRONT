package com.example.f4.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.OnItemListener
import com.example.f4.`interface`.Token
import com.example.f4.adapter.CalendarAdapter
import com.example.f4.data.Calendar
import com.example.f4.databinding.ActivityCalenderBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

//
class CalenderActivity : AppCompatActivity(), OnItemListener {

    private lateinit var binding: ActivityCalenderBinding
    //년월 변수
    lateinit var selectedDate: LocalDate

    var dateList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        //binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender)

        //현재 날짜
        selectedDate = LocalDate.now()


        binding.CalenderSetting.setOnClickListener {
            var cs_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(cs_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.CalenderCalender.setOnClickListener {
            var cc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(cc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.CalenderSpoon.setOnClickListener {
            var csp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(csp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.CalenderGroup.setOnClickListener {
            var cg_intent= Intent(this,GroupActivity::class.java)
            startActivity(cg_intent)
            overridePendingTransition(0, 0);
            finish()

        }

        //통신
        loadMyGroup(selectedDate)
        //화면설정
        setMonthView()

        //이전달 버튼 이벤트
        binding.preBtn.setOnClickListener{
            // 현재 월 -1 변수에 담기
            selectedDate =selectedDate.minusMonths(1)
            loadMyGroup(selectedDate)
            setMonthView()
        }

        //다음 달 버튼 이벤트
        binding.nextBtn.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            loadMyGroup(selectedDate)
            setMonthView()

        }


    }

    private fun setMonthView(){
        //년월 텍스트뷰 셋팅
        binding.monthYearText.text = YearMonthFromDate(selectedDate)

        //날짜 생성해서 리스트에 담기
//        val dayList = dayInMonthArray(selectedDate)
        //어댑터 초기화
//        val adapter = CalendarAdapter(dayList, testList, this, YearMonthFromDate(selectedDate))
        //어댑터 적용
//        binding.recyclerView.adapter = adapter

        //레이아웃 설정(열 7개)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        //레이아웃 적용
        binding.recyclerView.layoutManager = manager



    }
    private fun setAdapter(groupList : ArrayList<Calendar>){
        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray(selectedDate)
        //어댑터 초기화
        val adapter = CalendarAdapter(dayList, groupList, this, YearMonthFromDate(selectedDate))
        //어댑터 적용
        binding.recyclerView.adapter = adapter
    }

    //날짜 타입 설정 (월, 년)
    private fun monthYearFromDate(date: LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("MM.yyyy")

        //받아온 날짜를 해당 포맷으로 변경
        return date.format(formatter)
    }

    //날짜 타입 (년, 월)
    private fun YearMonthforServer(date: LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        //받아온 날짜를 해당 포맷으로 변경
        return date.format(formatter)
    }

    //날짜 타입 (년, 월)
    private fun YearMonthFromDate(date: LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        //받아온 날짜를 해당 포맷으로 변경
        return date.format(formatter)
    }

    //날짜 생성

    private fun dayInMonthArray(date: LocalDate): ArrayList<String>{
        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(date)

        //해당 월 마지막 날짜 가져오기(예: 28, 29, 30, 31)
        var lastDay = yearMonth.lengthOfMonth()

        //해당 월의 첫 번째 날 가져오기(예: 4월 1일)
        var firstDay= selectedDate.withDayOfMonth(1)

        //첫 번째 날 요일 가져오기 (월: 1, 일: 7)
        var dayofWeek = firstDay.dayOfWeek.value

        for(i in 1..41){
            if (i <= dayofWeek || i >(lastDay + dayofWeek)){
                dayList.add("")

            }else{
                Log.d("DAte", "Test"+ selectedDate + " ~~`" + (i-dayofWeek).toString())

                if (i-dayofWeek.toString().toInt() in 1..9){
                    Log.d("DAte", "Test"+ selectedDate + (i-dayofWeek).toString())
                    dayList.add( YearMonthFromDate(selectedDate) + ".0" +(i-dayofWeek).toString())
                    dateList.add( YearMonthFromDate(selectedDate) + ".0" +(i-dayofWeek).toString())
                }else{
                    dayList.add( YearMonthFromDate(selectedDate) + "." +(i-dayofWeek).toString())
                    dateList.add( YearMonthFromDate(selectedDate) + "." +(i-dayofWeek).toString())
                }

            }
        }

        return dayList
    }



    //아이템 클릭 이벤트
    override fun onItemClick(dayText: String) {

        var yearMonthDay =  dayText

        val intent = Intent(this,DietDayActivity::class.java)
        intent.putExtra("dateGetCalendar", yearMonthDay)

        startActivity(intent)

    }

    private fun loadMyGroup(selectDate: LocalDate){

        (application as MasterApplication).service.calender(Token.token,YearMonthforServer(selectDate)).enqueue(object :
            Callback<ArrayList<Calendar>> {
            override fun onFailure(call: Call<ArrayList<Calendar>>, t: Throwable) {
                Log.d("TEST fail : ", "fail  !!!  " + t.getLocalizedMessage())
            }
            override fun onResponse(call: Call<ArrayList<Calendar>>, response: Response<ArrayList<Calendar>>) {
                if (response.isSuccessful.not()) {
                    Log.d("Test fail : ", "response")
                    return
                }
                val groupList = response.body()
                Log.d("TEST response : ", "dateTime :${groupList} ")

                if(groupList != null){
                    setAdapter(groupList)
                }

            }
        })

    }



}