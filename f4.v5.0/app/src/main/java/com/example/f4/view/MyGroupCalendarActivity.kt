package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.OnItemListener
import com.example.f4.interfaces.Token
import com.example.f4.adapter.CalendarAdapter
import com.example.f4.data.Calendar
import com.example.f4.databinding.ActivityMyGroupCalendarBinding
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

    lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_group_calendar)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_group_calendar)

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

        setMonthView()

        loadMyGroupCalendar( userId, selectedDate.toString())

        binding.preBtn.setOnClickListener{
            selectedDate =selectedDate.minusMonths(1)
            loadMyGroupCalendar( userId, selectedDate.toString())
            setMonthView()
        }

        binding.nextBtn.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            loadMyGroupCalendar( userId, selectedDate.toString())
            setMonthView()
        }


    }
    private fun setAdapter(groupList : ArrayList<Calendar>){
        val dayList = dayInMonthArray(selectedDate)
        val adapter = CalendarAdapter(dayList, groupList, this, YearMonthFromDate(selectedDate))
        binding.recyclerView.adapter = adapter
    }

    private fun setMonthView(){
        binding.monthYearText.text = YearMonthFromDate(selectedDate)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        binding.recyclerView.layoutManager = manager
    }

    private fun YearMonthFromDate(date: LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        return date.format(formatter)
    }


    private fun dayInMonthArray(date: LocalDate): ArrayList<String>{
        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(date)

        var lastDay = yearMonth.lengthOfMonth()

        var firstDay= selectedDate.withDayOfMonth(1)

        var dayofWeek = firstDay.dayOfWeek.value

        for(i in 1..41){
            if (i <= dayofWeek || i >(lastDay + dayofWeek)){
                dayList.add("")

            }else{

                if (i-dayofWeek.toString().toInt() in 1..9){
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

    override fun onItemClick(dayText: String) {

        var yearMonthDay =  dayText

        val intent = Intent(this,MyGroupDietDayActivity::class.java)
        intent.putExtra("selectDate", dayText)
        intent.putExtra("groupId", groupId)
        intent.putExtra("userId", userId)
        intent.putExtra("userNickName", userNickName)


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
            }

            override fun onResponse(
                call: Call<ArrayList<Calendar>>,
                response: Response<ArrayList<Calendar>>
            ) {
                if (response.isSuccessful.not()) {
                    return
                }
                val groupList = response.body()

                if (groupList != null) {
                    setAdapter(groupList)
                }
            }
        })
    }

}