// 캘린더 - 음식추가 - 상세 1

package com.example.f4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.f4.databinding.FragmentAddFoodDialogBinding


class AddFoodDialogFragment : DialogFragment() {

    private var mBinding: FragmentAddFoodDialogBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 알림창이 띄워져있는 동안 배경 클릭 불가능
        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddFoodDialogBinding.inflate(inflater, container, false)

        // x 버튼 클릭 시 이전 액티비티로 돌아감
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

        // 상세보기로 넘어감
        binding.AddFoodPlusBtn.setOnClickListener {
            val dialog = AddFoodDialog2Fragment()
            dialog.show(childFragmentManager, "AddFoodDialog2Fragment")
        }

        // EditText의 내용이 캘린더-기록 화면으로 넘어가서 보여짐.
        // 일단 임시로 AddFoodActivity 화면으로 넘어가도록 해둠. Intent 부분만 수정하기.
        binding.AddFoodRemBtn.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, AddFoodActivity::class.java)
                nextIntent.putExtra("sendFoodAmountData", binding.inputEt.text.toString()) // putExtra - (저장될 값의 키, 저장될 값)
                startActivity(nextIntent)
                dismiss()
            }
        }

    }
}