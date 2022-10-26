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
import com.example.f4.databinding.DialogRecord1Binding

class RecordDialog1(context: Context, DialogInterface1: DialogInterface1) : Dialog(context) {

    private var mBinding : DialogRecord1Binding? = null
    private val binding get() = mBinding!!
    private var DialogInterface1:DialogInterface1? = null

    init {
        this.DialogInterface1 = DialogInterface1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogRecord1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCamera.setOnClickListener {
            this.DialogInterface1?.CameraClicked1()
            dismiss()
        }
        binding.btnGallery.setOnClickListener {
            this.DialogInterface1?.GalleryClicked1()
            dismiss()
        }
    }

}

interface DialogInterface1 {

    fun CameraClicked1()
    fun GalleryClicked1()

}