package com.example.f4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.DietDayAdapter
import com.example.f4.data.Diet
import com.example.f4.databinding.ActivityMyGroupDietDayBinding
import kotlinx.android.synthetic.main.activity_diet_day.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MyGroupDietDayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyGroupDietDayBinding

    var date=""
    var userId: Long = 0
    var groupId: Long =0
    var userNickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_group_diet_day)

        binding = ActivityMyGroupDietDayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dietRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!dietRecycle.canScrollVertically(1)) {

                }
            }
        })
        binding.DietdaySetting.setOnClickListener {
            var ds_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(ds_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.DietdayCalender.setOnClickListener {
            var dc_intent: Intent = Intent(this, CalenderActivity::class.java)
            startActivity(dc_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.DietdaySpoon.setOnClickListener {
            var dsp_intent: Intent = Intent(this, SpoonActivity::class.java)
            startActivity(dsp_intent)
            overridePendingTransition(0, 0);
            finish()

        }
        binding.DietdayGroup.setOnClickListener {
            var dg_intent= Intent(this,GroupActivity::class.java)
            startActivity(dg_intent)
            overridePendingTransition(0, 0);
            finish()

        }

        binding.backMyGroupDietDay.setOnClickListener {
            var intent = Intent(this, MyGroupCalendarActivity::class.java)
            intent.putExtra("groupId", groupId)
            intent.putExtra("userId", userId)
            intent.putExtra("userNickName", userNickName)
            startActivity(intent)
            finish()
        }

        if(intent.hasExtra("selectDate")){
            date = intent.getStringExtra("selectDate").toString()
            date=date.replace(".", "-")
        }
        if(intent.hasExtra("userId"))
            userId = intent.getLongExtra("userId", 0)
        if(intent.hasExtra("groupId"))
            groupId = intent.getLongExtra("groupId", 0)
        if(intent.hasExtra("userNickName"))
            userNickName = intent.getStringExtra("userNickName").toString()

        val foodId = "2"

        binding.selectedCalendarName.text = userNickName+"님의 캘린더"


        val selected_date =  LocalDate.parse(date)

        (application as MasterApplication).service.groupDiet(Token.token, userId, groupId , selected_date).enqueue(object :
            Callback<Diet>
        {
            override fun onFailure(call: Call<Diet>, t: Throwable) {
            }
            override fun onResponse(call: Call<Diet>, response: Response<Diet>) {
                val totalKcal = response.body()
                val dietList = response.body()
                if (dietList != null){
                    binding.SelectedDateId.text= dietList.date.replace("-",".")
                    binding.dietTotalKcal.text="총 "+dietList.totalKcal.toInt().toString()+"Kcal"
                    binding.eatDietCarbohydrate.text=doubleToString(dietList.eatNutrient.carbohydrate)
                    binding.needDietCarbohydrate.text = "/"+doubleToString(dietList.needNutrient.carbohydrate)
                    binding.eatDietProtein.text = doubleToString(dietList.eatNutrient.protein)
                    binding.needDietProtein.text = "/"+doubleToString(dietList.needNutrient.protein)
                    binding.eatDietFat.text = doubleToString(dietList.eatNutrient.fat)
                    binding.needDietFat.text = "/"+doubleToString(dietList.needNutrient.fat)
                    //stamp
                    checkStamp(dietList.stamp)

                }

                binding.dietRecycle.apply {
                    adapter = response.body()?.let { DietDayAdapter(this@MyGroupDietDayActivity, dietList?.date!!,foodId).build(it.meals) }
                    layoutManager =
                        LinearLayoutManager(this@MyGroupDietDayActivity, LinearLayoutManager.VERTICAL, false)
                }
            }

        })

    }

    fun doubleToString(value: Double): String {
        return value.toInt().toString()+"g"
    }

    private fun checkStamp(stamp : String){
        if (stamp != null){
            when (stamp) {
                "GREEN" -> {
                    binding.dietStampGoodImg.visibility = View.VISIBLE
                }
                "YELLOW" -> {
                    binding.dietStampSosoImg.visibility = View.VISIBLE

                }
                "RED" -> {
                    binding.dietStampBadImg.visibility = View.VISIBLE

                }
                else -> {
                }
            }
        }

    }


}