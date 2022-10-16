package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.OnItemListener
import com.example.f4.`interface`.Token
import com.example.f4.adapter.CalendarAdapter
import com.example.f4.data.Calendar
import com.example.f4.databinding.ActivityMyGroupCalendarBinding
import kotlinx.android.synthetic.main.my_group_item.*
import kotlinx.android.synthetic.main.my_group_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MyGroupCalendarActivity : AppCompatActivity(), OnItemListener {

    private lateinit var binding: ActivityMyGroupCalendarBinding
    var groupList: List<Calendar>? = null

    var dateList = ArrayList<String>()


    var groupId: Long = 0
    var userId:Long = 0
    var userNickName: String = ""

    //년월 변수
    lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_group_calendar)


        //binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_group_calendar)

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



        var date = LocalDate.now()
        groupId= intent.getLongExtra("groupId", 0)
        userId= intent.getLongExtra("userId", 0)
        userNickName= intent.getStringExtra("userNickName").toString()

        var groupCur: Int = 0
        if(intent.hasExtra("groupCur")) {
            groupCur = intent.getIntExtra("groupCur", 0) // groupCurrent
        }



        binding.selectedCalendarName.text = userNickName+"님의 캘린더"


        binding.BackMyGroupCalendar.setOnClickListener {
            var intent = Intent(this, MyGroupActivity::class.java)
            // 비비가 일단 추가해봄
            intent.putExtra("groupId", groupId)
            intent.putExtra("groupCur", groupCur)
            startActivity(intent)
            finish()
        }


        //화면 설정
        setMonthView()

        loadMyGroupCalendar( userId, selectedDate.toString())

        //이전달 버튼 이벤트
        binding.preBtn.setOnClickListener{
            // 현재 월 -1 변수에 담기
            selectedDate =selectedDate.minusMonths(1)
            loadMyGroupCalendar( userId, selectedDate.toString())
            setMonthView()
        }

        //다음 달 버튼 이벤트
        binding.nextBtn.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            loadMyGroupCalendar( userId, selectedDate.toString())
            setMonthView()
        }


    }
    private fun setAdapter(groupList : ArrayList<Calendar>){
        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray(selectedDate)
        //어댑터 초기화
        val adapter = CalendarAdapter(dayList, groupList, this, YearMonthFromDate(selectedDate))
        //어댑터 적용
        binding.recyclerView.adapter = adapter
    }

    private fun setMonthView(){
        //년월 텍스트뷰 셋팅
        binding.monthYearText.text = YearMonthFromDate(selectedDate)

        //레이아웃 설정(열 7개)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        //레이아웃 적용
        binding.recyclerView.layoutManager = manager
//

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

        val intent = Intent(this,MyGroupDietDayActivity::class.java)
        intent.putExtra("selectDate", dayText)
        Log.d("test", yearMonthDay)
        intent.putExtra("groupId", groupId)
        Log.d("test", groupId.toString())

        intent.putExtra("userId", userId)
        Log.d("test", userId.toString())

        intent.putExtra("userNickName", userNickName)
        Log.d("test", userNickName)


        startActivity(intent)
        finish()

    }

    private fun loadMyGroupCalendar(userId: Long, selectedDate: String) {

        (application as MasterApplication).service.groupCalendar(
            Token.token,
            userId,
            selectedDate.toString()
        ).enqueue(object :
            Callback<ArrayList<Calendar>> {
            override fun onFailure(call: Call<ArrayList<Calendar>>, t: Throwable) {
                //통신 실패
                Log.d("TEST fail : ", "내 그룹 통신 실패")
            }

            override fun onResponse(
                call: Call<ArrayList<Calendar>>,
                response: Response<ArrayList<Calendar>>
            ) {
                if (response.isSuccessful.not()) {
                    Log.d("Test fail : ", "response")
                    return
                }
                val groupList = response.body()
                Log.d("TEST response : ", "dateTime :${groupList} ")

                if (groupList != null) {
                    setAdapter(groupList)
                }
            }
        })
    }

}