package com.example.f4.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
//import com.example.f4.adapter.AddFoodDialogAdapter
import com.example.f4.databinding.FragmentAddFoodDialog2Binding



class AddFoodDialog2Fragment : DialogFragment() {

    private lateinit var binding: FragmentAddFoodDialog2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFoodDialog2Binding.inflate(inflater, container, false)

        val detailFoodName: String? = arguments?.getString("foodNameToDial2")
        val detailFoodKcal: String? = arguments?.getString("foodKcalToDial2")
        val detailFoodAmount: String? = arguments?.getString("foodAmountToDial2")
        val detailFoodcar: String? = arguments?.getString("foodCarToDial2")
        val detailFoodpro: String? = arguments?.getString("foodProToDial2")
        val detailFoodfat: String? = arguments?.getString("foodFatToDial2")
        val detailFoodpho: String? = arguments?.getString("foodPhoToDial2")
        val detailFooddie: String? = arguments?.getString("foodDieToDial2")
        val detailFoodmoi: String? = arguments?.getString("foodMoiToDial2")
        val detailFoodvit: String? = arguments?.getString("foodVitToDial2")
        val detailFoodfol: String? = arguments?.getString("foodFolToDial2")
        val detailFoodclc: String? = arguments?.getString("foodClcToDial2")
        val detailFoodsod: String? = arguments?.getString("foodSodToDial2")
        val detailFoodpot: String? = arguments?.getString("foodPotToDial2")
        val detailFoodmag: String? = arguments?.getString("foodMagToDial2")

        binding.addFoodDial2TvName.setText(detailFoodName)
        binding.addFoodDial2TvKcal.setText(detailFoodKcal)
        binding.addFoodDial2TvCount.setText(detailFoodAmount)
        binding.kcal1.setText(detailFoodcar)
        binding.kcal2.setText(detailFoodpro)
        binding.kcal3.setText(detailFoodfat)
        binding.kcal4.setText(detailFoodpho)
        binding.kcal5.setText(detailFooddie)
        binding.kcal6.setText(detailFoodmoi)
        binding.kcal7.setText(detailFoodvit)
        binding.kcal8.setText(detailFoodfol)
        binding.kcal9.setText(detailFoodclc)
        binding.kcal10.setText(detailFoodsod)
        binding.kcal11.setText(detailFoodpot)
        binding.kcal12.setText(detailFoodmag)

        binding.addFoodDial2Cancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}