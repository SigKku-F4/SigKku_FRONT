package com.example.f4

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.f4.databinding.DialogGroupBinding

class GroupDialog : DialogFragment() {

    // 뷰 바인딩 정의
    private lateinit var mBinding : DialogGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View {
        mBinding = DialogGroupBinding.inflate(inflater, container, false)
        val view = mBinding.root

        // 레이아웃 배경을 투명하게 해줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 그룹 생성 클릭 (Intent 부분 수정하기)
        mBinding.grCreation.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, AddFoodActivity::class.java)
                startActivity(nextIntent)

                dismiss()
            }
        }

        // 그룹 가입 클릭 (Intent 부분 수정하기)
        mBinding.grJoin.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, AddFoodActivity::class.java)
                startActivity(nextIntent)

                dismiss()
            }
        }

        return view

    }

}
