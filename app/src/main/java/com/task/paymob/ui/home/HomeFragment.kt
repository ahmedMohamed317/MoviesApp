package com.task.paymob.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.task.paymob.viewmodel.home.HomeViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModel<HomeViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater , container , false)


    override fun initClicks() {

    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {

    }

    override fun initSetAdapter() {
    }

    override fun initToolBar() {
        binding.toolbar.backBtn.visibility = View.GONE
        binding.toolbar.tvTitle.text = ""
    }




}