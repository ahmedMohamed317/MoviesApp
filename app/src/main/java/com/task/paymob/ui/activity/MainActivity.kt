package com.task.paymob.ui.activity

import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.task.paymob.R
import com.task.paymob.base.BaseActivity
import com.task.paymob.databinding.ActivityMainBinding
import com.task.paymob.ui.no_connection.NoConnectionActivity
import com.task.paymob.utils.network.ConnectivityObserver
import com.task.paymob.utils.network.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navHostFragment: NavHostFragment
    private val networkConnectivityObserver by lazy { NetworkConnectivityObserver(this) }
    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun initClicks() {
    }

    override fun onCreateInit() {
        initViews()
        // disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleScope.launch {
            try {
                // check network connection
                checkNetworkObserver()
            }catch (e :Exception){
                Timber.tag("TAG").d(e.message.toString())
            }
        }
    }

    private fun initViews() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return
    }

    private suspend fun checkNetworkObserver() {
        networkConnectivityObserver.observe().collectLatest {
            when (it) {
                ConnectivityObserver.Status.UnAvailable, ConnectivityObserver.Status.Losing, ConnectivityObserver.Status.Lost
                -> {
                    Timber.d("No Connection")
                    val intent = Intent(this, NoConnectionActivity::class.java)
                    startActivity(intent)
                }

                else -> {
                }
            }
        }
    }
}

