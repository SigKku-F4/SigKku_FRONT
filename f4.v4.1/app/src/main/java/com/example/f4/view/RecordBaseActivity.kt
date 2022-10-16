package com.example.f4.view

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

abstract class RecordBaseActivity : AppCompatActivity() {

    abstract fun permissionGranted(requestCode: Int)
    abstract fun permissionDenied(requestCode: Int)

    //권한 검사
    fun requirePermissions(permissions:Array<String>, requestCode: Int) {
        //API 버전이 마시멜로 미만이면 권한 처리가 필요
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all {it == PackageManager.PERMISSION_GRANTED}) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }
}

