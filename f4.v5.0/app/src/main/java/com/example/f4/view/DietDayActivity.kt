package com.example.f4.view

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.DietDayAdapter
import com.example.f4.data.*
import com.example.f4.databinding.ActivityDietDayBinding
import kotlinx.android.synthetic.main.activity_diet_day.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class DietDayActivity : AppCompatActivity() {

    private lateinit var activityResultGallery: ActivityResultLauncher<Intent>


    private lateinit var binding: ActivityDietDayBinding

    var dateId : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_day)


        binding = ActivityDietDayBinding.inflate(layoutInflater)
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

        binding.backCalender.setOnClickListener {
            var intent = Intent(this, CalenderActivity::class.java)
            startActivity(intent)
            finish()
        }


        activityResultGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    absolutelyPath(uri)

                }
            }
        }

        dateId = findViewById<TextView>(R.id.SelectedDateId)

        var dateGetCalendar = intent.getStringExtra("dateGetCalendar")
        var dateGetRecord = intent.getStringExtra("dateGetRecord")

        if (dateGetCalendar != null ){
            dateGetCalendar = dateGetCalendar.toString().replace(".", "-")
            loadDietDay(LocalDate.parse(dateGetCalendar))
        }

        if (dateGetRecord != null){
            dateGetRecord = dateGetRecord.toString()
            loadDietDay(LocalDate.parse(dateGetRecord))
        }

    }
    fun doubleToString(value: Double): String {
        return value.toInt().toString()+"g"
    }
    private fun loadDietDay(day:LocalDate) {
        (application as MasterApplication).service.diet(Token.token,day).enqueue(object :
            Callback<Diet>
        {
            override fun onFailure(call: Call<Diet>, t: Throwable) {
            }
            override fun onResponse(call: Call<Diet>, response: Response<Diet>) {
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
                    checkStamp(dietList.stamp)
                }

                var foodId = intent.getStringExtra("foodId").toString()
                if (foodId == null){
                    foodId ="0"
                }

                binding.btnAddDiet.setOnClickListener {
                    var intent = Intent(this@DietDayActivity, RecordActivity::class.java)
                    intent.putExtra("date",dietList?.date)
                    startActivity(intent)
                    finish()
                }
                binding.dietRecycle.apply {
                    if (dietList != null)
                        adapter = DietDayAdapter(this@DietDayActivity, dietList.date, foodId).build(dietList.meals)
                    layoutManager =
                        LinearLayoutManager(this@DietDayActivity, LinearLayoutManager.VERTICAL, false)
                }
            }
        })
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

    fun absolutelyPath(path: Uri): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor = contentResolver.query(path, proj, null, null, null)!!
        var index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()
        var result = c.getString(index)
        return result
    }

}