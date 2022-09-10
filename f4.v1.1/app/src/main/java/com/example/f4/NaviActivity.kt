package com.example.f4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.f4.databinding.ActivityNaviBinding


class NaviActivity : AppCompatActivity() {

   private lateinit var mBinding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavi)

        changeFragment(Frag1())

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_calendar -> changeFragment(Frag1())
                R.id.action_food -> changeFragment(Frag2())
                R.id.action_group -> changeFragment(Frag3())
                R.id.action_setting -> changeFragment(Frag4())
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
    }

}