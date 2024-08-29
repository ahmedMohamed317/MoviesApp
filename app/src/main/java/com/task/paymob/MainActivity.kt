package com.task.paymob

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.task.paymob.base.BaseActivity
import com.task.paymob.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navHostFragment: NavHostFragment

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun initClicks() {
    }

    override fun onCreateInit() {
        initViews()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initViews() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return
    }


}

