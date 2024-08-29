package com.task.paymob.ui.no_connection

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.task.paymob.R
import com.task.paymob.databinding.ActivityNoConnectionBinding
import com.task.paymob.utils.LanguageUtils.onAttach
import com.task.paymob.utils.NetworkUtils
import com.task.paymob.utils.network.ConnectivityObserver
import com.task.paymob.utils.network.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoConnectionActivity : AppCompatActivity() {

    private val networkConnectivityObserver by lazy { NetworkConnectivityObserver(this) }
    private lateinit var binding: ActivityNoConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleScope.launch {
            checkNetworkObserver()
        }
        hideStatus()
        initClick(binding)
        handleBars(false)
        updateStatusBarColor(R.color.white)
        adjustIconsColor(false)
    }

    private fun initClick(binding: ActivityNoConnectionBinding) {
        binding.btnRetry.setOnClickListener {
            //checkConnection(networkUtils)
            lifecycleScope.launch {
                checkNetworkObserver()
            }
        }
    }

    private fun checkConnection(networkUtils: NetworkUtils) {
        networkUtils.observe(this) { isConnected ->
            if (isConnected) {
                finish()
            }
        }
    }

    private suspend fun checkNetworkObserver() {
        networkConnectivityObserver.observe().collectLatest {
            when (it) {
                ConnectivityObserver.Status.Available -> {
                    finish()
                }
                else -> {}
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(onAttach(newBase!!))
    }


    private fun hideStatus(){
        supportActionBar?.hide()
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    fun handleBars(isHidden: Boolean) {

        when (isHidden) {
            true -> {
                (this as AppCompatActivity).supportActionBar?.hide()
                this.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }

            else -> {
                (this as AppCompatActivity).supportActionBar?.hide()
                this.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

        }

    }

    fun adjustIconsColor(isWhite : Boolean) {
        if (!isWhite) {
            this.window?.getDecorView()?.setSystemUiVisibility(
                this?.window?.getDecorView()
                    ?.getSystemUiVisibility()!! or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
        } else {

            this?.window?.decorView?.systemUiVisibility = this?.window?.decorView
                ?.systemUiVisibility!! and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
    protected fun updateStatusBarColor(color: Int) {
        // Color must be in hexadecimal fromat
        val hexColor = Integer.toHexString(ContextCompat.getColor(this, color))
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#$hexColor")
    }
}