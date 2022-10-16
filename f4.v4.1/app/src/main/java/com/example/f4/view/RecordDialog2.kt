package com.example.f4.view

import android.app.Dialog
import android.content.Context
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
import com.example.f4.databinding.DialogRecord2Binding

class RecordDialog2(context: Context, DialogInterface2: DialogInterface2) : Dialog(context) {

    // 뷰 바인딩 정의
//    private lateinit var mBinding : DialogRecordBinding
    private var mBinding : DialogRecord2Binding? = null
    private val binding get() = mBinding!!
    private var DialogInterface2:DialogInterface2? = null

    init {
        this.DialogInterface2 = DialogInterface2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogRecord2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 배경 투명하게
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCamera.setOnClickListener {
            this.DialogInterface2?.CameraClicked2()
            dismiss()
        }
        binding.btnGallery.setOnClickListener {
            this.DialogInterface2?.GalleryClicked2()
            dismiss()
        }
    }
}

interface DialogInterface2 {

    fun CameraClicked2()
    fun GalleryClicked2()

}