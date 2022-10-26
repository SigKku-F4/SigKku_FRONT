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
import com.example.f4.databinding.DialogRecord3Binding

class RecordDialog3(context: Context, DialogInterface3: DialogInterface3) : Dialog(context) {

    private var mBinding : DialogRecord3Binding? = null
    private val binding get() = mBinding!!
    private var DialogInterface3:DialogInterface3? = null

    init {
        this.DialogInterface3 = DialogInterface3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogRecord3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCamera.setOnClickListener {
            this.DialogInterface3?.CameraClicked3()
            dismiss()
        }
        binding.btnGallery.setOnClickListener {
            this.DialogInterface3?.GalleryClicked3()
            dismiss()
        }
    }
}

interface DialogInterface3 {

    fun CameraClicked3()
    fun GalleryClicked3()

}