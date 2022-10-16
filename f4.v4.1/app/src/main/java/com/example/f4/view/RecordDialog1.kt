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

    // 뷰 바인딩 정의
//    private lateinit var mBinding : DialogRecordBinding
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

        // 배경 투명하게
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

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        saveInstanceState: Bundle?
//    ): View {
//        mBinding = DialogRecordBinding.inflate(inflater, container, false)
//        val view = mBinding.root
//
//        // 레이아웃 배경을 투명하게 해줌
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        //카메라 클릭
//        mBinding.btnCamera.setOnClickListener {
//            activity?.let {
//                val nextIntent = Intent(context, GroupCreateActivity::class.java)
//                startActivity(nextIntent)
//                dismiss()
//            }
//        }
//
//        //갤러리 클릭
//        mBinding.btnGallery.setOnClickListener {
//            activity?.let {
//                val nextIntent = Intent(context, GroupSignActivity::class.java)
//                startActivity(nextIntent)
//                dismiss()
//            }
//        }
//        return view
//    }
}

interface DialogInterface1 {

    fun CameraClicked1()
    fun GalleryClicked1()

}

//class MyCustomDialog(context:Context, myCustomDialogInterface: MyCustomDialogInterface) : Dialog(context) {
//
//    private var mBinding : CustomDialogBinding? = null
//    private val binding get() = mBinding!!
//
//    private var myCustomDialogInterface:MyCustomDialogInterface? = null
//
//    // 인터페이스 연결
//    init {
//        this.myCustomDialogInterface = myCustomDialogInterface
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mBinding = CustomDialogBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 배경 투명하게
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        binding.subscribeBtn.setOnClickListener {
//            this.myCustomDialogInterface?.onSubscribeBtnClicked()
//        }
//        binding.likeBtn.setOnClickListener {
//            this.myCustomDialogInterface?.onLikedBtnClicked()
//        }
//    }
//}