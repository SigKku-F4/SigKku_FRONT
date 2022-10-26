package com.example.f4.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.f4.interfaces.RetrofitClient
import com.example.f4.interfaces.RetrofitService
import com.example.f4.interfaces.Token
import com.example.f4.data.Addfooddetail
import com.example.f4.databinding.FragmentAddFoodDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.math.roundToInt


class AddFoodDialogFragment : DialogFragment() {

    private var mBinding: FragmentAddFoodDialogBinding? = null
    private val binding get() = mBinding!!

    private val retrofit: Retrofit = RetrofitClient.getInstance()
    private val api: RetrofitService = retrofit.create(RetrofitService::class.java)

    var toDetailName: String = ""
    var toDetailKcal: Double = 0.0
    var toDetailAmount: Double = 0.0
    var toDetailcar: Double = 0.0
    var toDetailpro: Double = 0.0
    var toDetailfat: Double = 0.0
    var toDetailpho: Double = 0.0
    var toDetaildie: Double = 0.0
    var toDetailmoi: Double = 0.0
    var toDetailvit: Double = 0.0
    var toDetailfol: Double = 0.0
    var toDetailclc: Double = 0.0
    var toDetailsod: Double = 0.0
    var toDetailpot: Double = 0.0
    var toDetailmag: Double = 0.0

    // 음식 기록으로 넘길 kcal 데이터
    var toRecordKcal: Double = 0.0
    var foodId: String = ""
    var foodName: String = ""
    var date: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false

        foodId = arguments?.getString("foodIdToDial").toString()
        date = arguments?.getString("date").toString()
        Runnable {
            if (foodId != null) {
                api.detailFoods(Token.token, foodId.toLong()).enqueue(object : Callback<Addfooddetail> {
                    override fun onFailure(call: Call<Addfooddetail>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<Addfooddetail>, response: Response<Addfooddetail>) {
                        if (response.isSuccessful.not()) {
                            return
                        }

                        toDetailName = response.body()?.foodName.toString()
                        toDetailKcal = response.body()?.nutrient?.calorie!!
                        toDetailAmount = response.body()?.oneSupplyAmount!!
                        toDetailcar = response.body()?.nutrient?.carbohydrate!!
                        toDetailpro = response.body()?.nutrient?.protein!!
                        toDetailfat = response.body()?.nutrient?.fat!!
                        toDetailpho = response.body()?.nutrient?.phosphorus!!
                        toDetaildie = response.body()?.nutrient?.dietaryFiber!!
                        toDetailmoi = response.body()?.nutrient?.moisutre!!
                        toDetailvit = response.body()?.nutrient?.vitaminB12!!
                        toDetailfol = response.body()?.nutrient?.folicAcid!!
                        toDetailclc = response.body()?.nutrient?.calcium!!
                        toDetailsod = response.body()?.nutrient?.sodium!!
                        toDetailpot = response.body()?.nutrient?.potassium!!
                        toDetailmag = response.body()?.nutrient?.magnesium!!

                        toRecordKcal = response.body()?.nutrient?.calorie!!

                        foodName = response.body()?.foodName.toString()
                        val category = response.body()?.category
                        val oneSupplyAmount = response.body()?.oneSupplyAmount
                        val kcal = response.body()?.nutrient?.calorie
                        val car = response.body()?.nutrient?.carbohydrate
                        val pro = response.body()?.nutrient?.protein
                        val fat = response.body()?.nutrient?.fat

                        mBinding?.name?.setText("${foodName}")
                        mBinding?.oneSupplyAmount?.setText("${oneSupplyAmount}")
                        if (kcal != null) {
                            mBinding?.subNutrient1?.setText("${(kcal*100).roundToInt()/100f}")
                        }
                        if (car != null) {
                            mBinding?.carbohydrate?.setText("${(car*100).roundToInt()/100f}")
                        }
                        if (pro != null) {
                            mBinding?.protein?.setText("${(pro*100).roundToInt()/100f}")
                        }
                        if (fat != null) {
                            mBinding?.fat?.setText("${(fat*100).roundToInt()/100f}")
                        }
                    }
                })
            }
        }.run()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddFoodDialogBinding.inflate(inflater, container, false)

        binding.addFoodDialCancel.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AddFoodPlusBtn.setOnClickListener {
            val dialog = AddFoodDialog2Fragment()
            val bundle = Bundle()
            bundle.putString("foodNameToDial2", toDetailName)
            bundle.putString("foodKcalToDial2", ((toDetailKcal*100).roundToInt()/100f).toString())
            bundle.putString("foodAmountToDial2", ((toDetailAmount*100).roundToInt()/100f).toString())
            bundle.putString("foodCarToDial2", ((toDetailcar*100).roundToInt()/100f).toString())
            bundle.putString("foodProToDial2", ((toDetailpro*100).roundToInt()/100f).toString())
            bundle.putString("foodFatToDial2", ((toDetailfat*100).roundToInt()/100f).toString())
            bundle.putString("foodPhoToDial2", ((toDetailpho*100).roundToInt()/100f).toString())
            bundle.putString("foodDieToDial2", ((toDetaildie*100).roundToInt()/100f).toString())
            bundle.putString("foodMoiToDial2", ((toDetailmoi*100).roundToInt()/100f).toString())
            bundle.putString("foodVitToDial2", ((toDetailvit*100).roundToInt()/100f).toString())
            bundle.putString("foodFolToDial2", ((toDetailfol*100).roundToInt()/100f).toString())
            bundle.putString("foodClcToDial2", ((toDetailclc*100).roundToInt()/100f).toString())
            bundle.putString("foodSodToDial2", ((toDetailsod*100).roundToInt()/100f).toString())
            bundle.putString("foodPotToDial2", ((toDetailpot*100).roundToInt()/100f).toString())
            bundle.putString("foodMagToDial2", ((toDetailmag*100).roundToInt()/100f).toString())

            dialog.arguments = bundle
            dialog.show(childFragmentManager, "AddFoodDialog2Fragment")
        }

        var id: Long = foodId.toLong()
        binding.AddFoodRemBtn.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, RecordActivity::class.java)

                var amountEt = binding.inputEt.text.toString()
                var amount: Int = amountEt.toInt()
                var kcal = toRecordKcal*(amount.toDouble()/100)

                nextIntent.putExtra("foodListId", id) // foodListId
                nextIntent.putExtra("FoodName", foodName)
                nextIntent.putExtra("Amount", amount) // amount
                nextIntent.putExtra("Kcal", kcal) // kcal
                nextIntent.putExtra("date", date) // date
                getActivity()?.setResult(Activity.RESULT_OK, nextIntent)
                getActivity()?.finish()
            }
        }
    }
}