package com.example.f4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.f4.databinding.ActivityNaviBinding

private const val TAG_CALENDER = "calender_fragment"
private const val TAG_SPOON = "spoon_fragment"
private const val TAG_GROUP = "group_fragment"
private const val TAG_SETTING = "setting_fragment"

class NaviActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_CALENDER, CalenderFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.calenderFragment -> setFragment(TAG_CALENDER, CalenderFragment())
                R.id.spoonFragment -> setFragment(TAG_SPOON, SpoonFragment())
                R.id.groupFragment-> setFragment(TAG_GROUP, GroupFragment())
                R.id.settingFragment-> setFragment(TAG_SETTING, SettingFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val calender = manager.findFragmentByTag(TAG_CALENDER)
        val spoon = manager.findFragmentByTag(TAG_SPOON)
        val group = manager.findFragmentByTag(TAG_GROUP)
        val setting = manager.findFragmentByTag(TAG_SETTING)

        if (calender != null){
            fragTransaction.hide(calender)
        }

        if (spoon != null){
            fragTransaction.hide(spoon)
        }

        if (group != null) {
            fragTransaction.hide(group)
        }

        if (setting != null) {
            fragTransaction.hide(setting)
        }

        if (tag == TAG_CALENDER) {
            if (calender!=null){
                fragTransaction.show(calender)
            }
        }
        else if (tag == TAG_SPOON) {
            if (spoon != null) {
                fragTransaction.show(spoon)
            }
        }

        else if (tag == TAG_GROUP){
            if (group != null){
                fragTransaction.show(group)
            }
        }

        else if (tag == TAG_SETTING){
            if (setting != null){
                fragTransaction.show(setting)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}