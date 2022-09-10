package com.example.f4.view

import android.app.Activity
import android.content.Intent
import android.content.UriPermission
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.f4.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_change_info.*
import kotlinx.android.synthetic.main.activity_group_sign.*
import kotlinx.android.synthetic.main.activity_record.*
import java.util.jar.Manifest
import javax.security.auth.callback.PasswordCallback

class RecordActivity : AppCompatActivity() {

//    //권한 정의
//    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
//    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
//        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//    //권한 플래그 값 정의
//    val FLAG_PERM_CAMERA = 98
//    val FLAG_PERM_STORAGE = 99
//
//    //카메라 갤러리 호출 플래그 값 정의
//    val FLAG_REQ_CAMERA = 98
//    val FLAG_PEQ_STORAGE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

//        btn_photo.setOnClickListener {
//            if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
//                setViews()
//            }
//        }


        back_setting.setOnClickListener {
            var back_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(back_intent)
            finish()
        }

        btn_save.setOnClickListener {
            var save_intent: Intent = Intent(this, SettingActivity::class.java)
            startActivity(save_intent)
            finish()
        }

    }
//
//    fun setViews() {
//        btn_add_food.setOnClickListener{
//            openCamera()
//        }
//    }
//
//    fun openCamera() {
//        if (checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)) {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startForResult.launch(intent)
//        }
//    }
//
//    private val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//
//            if (result.resultCode == RESULT_OK) {
//                val intent: Intent = result.data!!
//                when (1) {
//                    FLAG_REQ_CAMERA -> {
//                        if (intent?.extras?.get("data") != null) {
//                            val bitmap = intent?.extras?.get("data") as Bitmap
//                            img_food1.setImageBitmap(bitmap)
//                        }
//                    }
//                }
//
//            }
//        }
//
//    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            for (permission in permissions) {
//                if (ContextCompat.checkSelfPermission(this, permission) !=
//                    PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(this, permissions, flag)
//                    return false
//                }
//            }
//        }
//        return false
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            FLAG_PERM_STORAGE -> {
//                for (grant in grantResults) {
//                    if (grant != PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "음식 추가를 실패했습니다. 저장소 권한을 승인해주세요.",
//                        Toast.LENGTH_LONG).show()
//                        finish()
//                        return
//                    }
//                }
//                setViews()
//            }
//            FLAG_PERM_CAMERA -> {
//                for (grant in grantResults) {
//                    if (grant != PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "음식 추가를 실패했습니다. 카메라 권한을 승인해주세요.",
//                            Toast.LENGTH_LONG).show()
//                        finish()
//                    }
//                }
//            }
//        }
//    }
}