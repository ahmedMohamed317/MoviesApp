package com.task.paymob.ui.home.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.task.paymob.R
import com.task.paymob.viewmodel.home.HomeViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentHomeBinding
import com.task.paymob.model.Movie
import com.task.paymob.ui.home.adapter.HomeMoviesAdapter
import com.task.paymob.utils.DialogUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModel<HomeViewModel>()
    private val homeMoviesAdapter: HomeMoviesAdapter by lazy {
        HomeMoviesAdapter(::navigateToMovieDetailsFragment)
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater , container , false)


    override fun initClicks() {

    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
        getMoviesForHome()
    }

    override fun initSetAdapter() {
        binding.moviesRv.adapter = homeMoviesAdapter
    }

    override fun initToolBar() {
        binding.toolbar.backBtn.visibility = View.GONE
        binding.toolbar.tvTitle.text = "Home"
    }


    private fun getMoviesForHome(year: String = "2024" , sortBy: String = "" , page : Int = 1) {
        DialogUtil.showDialog(requireActivity())
        homeViewModel.getMoviesForHome(year, sortBy, page)
        homeViewModel.responseGetMovies.observe(viewLifecycleOwner) {

            DialogUtil.dismissDialog()
            if (it.success != false) {
                homeMoviesAdapter.submitList(it.results)
                binding.noResultsTv.visibility = View.GONE
            } else {
                showSnackbar(it.message)
                binding.noResultsTv.visibility = View.VISIBLE

            }
        }
        homeViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
            DialogUtil.dismissDialog()
            binding.noResultsTv.visibility = View.VISIBLE
        }


    }
    private fun navigateToMovieDetailsFragment(movie: Movie) {
        if (checkCurrentDestination(R.id.homeFragment)) {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie)
            findNavController().navigate(action)

        }
    }
}