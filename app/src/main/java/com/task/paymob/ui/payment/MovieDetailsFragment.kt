package com.task.paymob.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.task.paymob.viewmodel.movies_details.MoviesDetailsViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentMovieDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val moviesDetailsViewModel by viewModel<MoviesDetailsViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater , container , false)


    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            navigateUp()
        }

    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
    }

    override fun initSetAdapter() {
    }

    override fun initToolBar() {
        binding.toolbar.tvTitle.text = ""

    }
    private fun navigateUp(){
        findNavController().navigateUp()
    }


}