package com.ldlldl.bottomnavi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ldlldl.bottomnavi.databinding.ActivityMainBinding


class BottomActivity : AppCompatActivity() {

   private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
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