package com.example.f4.view

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

    private lateinit var mBinding : DialogGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View {
        mBinding = DialogGroupBinding.inflate(inflater, container, false)
        val view = mBinding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mBinding.grCreation.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, GroupCreateActivity::class.java)
                startActivity(nextIntent)

                dismiss()
            }
        }

        mBinding.grJoin.setOnClickListener {
            activity?.let {
                val nextIntent = Intent(context, GroupSignActivity::class.java)
                startActivity(nextIntent)

                dismiss()
            }
        }

        return view

    }

}
