package com.example.f4.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.AddFoodListAdapter
import com.example.f4.data.Addfood
import com.example.f4.data.Foodlist
import com.example.f4.databinding.ActivityAddFoodBinding
import kotlinx.android.synthetic.main.activity_add_food.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFoodActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityAddFoodBinding

    private lateinit var foodListView: RecyclerView
    lateinit var addFoodAdapter: AddFoodListAdapter

    var data: String = ""
    var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        var getDate = intent.getStringExtra("date").toString()
        date = getDate

        mBinding.BackAddFood.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }

        foodListView = findViewById(R.id.foodListView)

        mBinding.searchFood.setOnKeyListener {v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                data = searchFood.text.toString()
                loadFoodLists(data)
                handled = true
            }

            handled

        }

    }

    private fun loadFoodLists(name: String) {
        var query: HashMap<String, Int> = hashMapOf("page" to 0, "size" to 30)

        (application as MasterApplication).service.addFoods(
            Token.token, name, query.get("page")!!, query.get("size")!!
        ).enqueue(object :
            Callback<Addfood>
        {
            override fun onFailure(call: Call<Addfood>, t: Throwable) {
            }
            override fun onResponse(call: Call<Addfood>, response: Response<Addfood>) {


                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        val data = response.body()?.foodList

                        if (data != null) {
                            setAdapter(data, date)
                        }
                    }

                val total_elements = response.body()?.totalElements.toString()
                val page_number = response.body()?.pageNumber.toString()
                val food_list = response.body()?.foodList

            }
        })
    }

    private fun setAdapter(foodList: List<Foodlist>, date: String) {
        addFoodAdapter = AddFoodListAdapter(this, foodList, date, supportFragmentManager)
        foodListView.adapter = addFoodAdapter
        foodListView.layoutManager = LinearLayoutManager(this)
    }



}