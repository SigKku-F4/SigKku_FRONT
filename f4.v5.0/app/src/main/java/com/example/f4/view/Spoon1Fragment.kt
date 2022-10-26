package com.example.f4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.f4.interfaces.RetrofitClient
import com.example.f4.interfaces.RetrofitService
import com.example.f4.interfaces.Token
import com.example.f4.data.Recommendedfood
import com.example.f4.databinding.FragmentSpoon1Binding
import kotlinx.android.synthetic.main.fragment_spoon1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate
import kotlin.math.roundToInt

class Spoon1Fragment : Fragment() {

    private var mBinding: FragmentSpoon1Binding? = null
    private val binding get() = mBinding!!

    private val retrofit: Retrofit = RetrofitClient.getInstance()
    private val api: RetrofitService = retrofit.create(RetrofitService::class.java)

    var date: LocalDate = LocalDate.now()
    var query: HashMap<String, Int> = hashMapOf("page" to 0, "size" to 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Runnable {
            api.recommendFoods(
                Token.token, date.toString(), query.get("page")!!, query.get("size")!!
            ).enqueue(object :
                Callback<Recommendedfood> {
                override fun onFailure(call: Call<Recommendedfood>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<Recommendedfood>,
                    response: Response<Recommendedfood>
                ) {
                    mBinding?.spoon1linear1?.visibility = View.INVISIBLE
                    mBinding?.spoon1linear2?.visibility = View.INVISIBLE
                    mBinding?.spoon1linear3?.visibility = View.INVISIBLE
                    mBinding?.spoon1linear4?.visibility = View.INVISIBLE
                    mBinding?.spoon1linear5?.visibility = View.INVISIBLE

                    if (response.isSuccessful.not()) {
                        return
                    }

                    val spoonData = response.body()
                    if (spoonData != null) {

                        if (spoonData.pageMeta.totalElements.toInt() == 0) {
                        } else {
                            if (spoonData.pageMeta.totalElements.toInt() >= 1) {
                                spoon1linear1.visibility = View.VISIBLE
                                val food_name1 = spoonData?.foodLists?.get(0).foodName
                                val food_amount1 = spoonData?.foodLists?.get(0).oneSupplyAmount
                                val food_kcal1 = spoonData?.foodLists?.get(0).nutrient.calorie
                                val food_car1 = spoonData?.foodLists?.get(0).nutrient.carbohydrate
                                val food_pro1 = spoonData?.foodLists?.get(0).nutrient.protein
                                val food_fat1 = spoonData?.foodLists?.get(0).nutrient.fat

                                mBinding?.spoon1Name1?.setText("${food_name1}")
                                mBinding?.spoon1Kcal1?.setText("${(food_kcal1 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1G1?.setText("${food_amount1}")
                                mBinding?.spoon1Car1?.setText("${(food_car1 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Pro1?.setText("${(food_pro1 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Fat1?.setText("${(food_fat1 * 100).roundToInt() / 100f}")
                            }
                            if (spoonData.pageMeta.totalElements.toInt() >= 2) {
                                spoon1linear2.visibility = View.VISIBLE
                                val food_name2 = spoonData?.foodLists?.get(1).foodName
                                val food_amount2 = spoonData?.foodLists?.get(1).oneSupplyAmount
                                val food_kcal2 = spoonData?.foodLists?.get(1).nutrient.calorie
                                val food_car2 = spoonData?.foodLists?.get(1).nutrient.carbohydrate
                                val food_pro2 = spoonData?.foodLists?.get(1).nutrient.protein
                                val food_fat2 = spoonData?.foodLists?.get(1).nutrient.fat

                                mBinding?.spoon1Name2?.setText("${food_name2}")
                                mBinding?.spoon1Kcal2?.setText("${(food_kcal2 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1G2?.setText("${food_amount2}")
                                mBinding?.spoon1Car2?.setText("${(food_car2 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Pro2?.setText("${(food_pro2 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Fat2?.setText("${(food_fat2 * 100).roundToInt() / 100f}")
                            }
                            if (spoonData.pageMeta.totalElements.toInt() >= 3) {
                                spoon1linear3.visibility = View.VISIBLE

                                val food_name3 = spoonData?.foodLists?.get(2).foodName
                                val food_amount3 = spoonData?.foodLists?.get(2).oneSupplyAmount
                                val food_kcal3 = spoonData?.foodLists?.get(2).nutrient.calorie
                                val food_car3 = spoonData?.foodLists?.get(2).nutrient.carbohydrate
                                val food_pro3 = spoonData?.foodLists?.get(2).nutrient.protein
                                val food_fat3 = spoonData?.foodLists?.get(2).nutrient.fat

                                mBinding?.spoon1Name3?.setText("${food_name3}")
                                mBinding?.spoon1Kcal3?.setText("${(food_kcal3 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1G3?.setText("${food_amount3}")
                                mBinding?.spoon1Car3?.setText("${(food_car3 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Pro3?.setText("${(food_pro3 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Fat3?.setText("${(food_fat3 * 100).roundToInt() / 100f}")
                            }
                            if (spoonData.pageMeta.totalElements.toInt() >= 4) {
                                spoon1linear4.visibility = View.VISIBLE

                                val food_name4 = spoonData?.foodLists?.get(3).foodName
                                val food_amount4 = spoonData?.foodLists?.get(3).oneSupplyAmount
                                val food_kcal4 = spoonData?.foodLists?.get(3).nutrient.calorie
                                val food_car4 = spoonData?.foodLists?.get(3).nutrient.carbohydrate
                                val food_pro4 = spoonData?.foodLists?.get(3).nutrient.protein
                                val food_fat4 = spoonData?.foodLists?.get(3).nutrient.fat

                                mBinding?.spoon1Name4?.setText("${food_name4}")
                                mBinding?.spoon1Kcal4?.setText("${(food_kcal4 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1G4?.setText("${food_amount4}")
                                mBinding?.spoon1Car4?.setText("${(food_car4 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Pro4?.setText("${(food_pro4 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Fat4?.setText("${(food_fat4 * 100).roundToInt() / 100f}")
                            }
                            if (spoonData.pageMeta.totalElements.toInt() >= 5) {
                                spoon1linear5.visibility = View.VISIBLE

                                val food_name5 = spoonData?.foodLists?.get(4).foodName
                                val food_amount5 = spoonData?.foodLists?.get(4).oneSupplyAmount
                                val food_kcal5 = spoonData?.foodLists?.get(4).nutrient.calorie
                                val food_car5 = spoonData?.foodLists?.get(4).nutrient.carbohydrate
                                val food_pro5 = spoonData?.foodLists?.get(4).nutrient.protein
                                val food_fat5 = spoonData?.foodLists?.get(4).nutrient.fat

                                mBinding?.spoon1Name5?.setText("${food_name5}")
                                mBinding?.spoon1Kcal5?.setText("${(food_kcal5 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1G5?.setText("${food_amount5}")
                                mBinding?.spoon1Car5?.setText("${(food_car5 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Pro5?.setText("${(food_pro5 * 100).roundToInt() / 100f}")
                                mBinding?.spoon1Fat5?.setText("${(food_fat5 * 100).roundToInt() / 100f}")
                            }
                        }
                    }
                }
            })
        }.run()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSpoon1Binding.inflate(inflater, container, false)
        return binding.root
    }

}