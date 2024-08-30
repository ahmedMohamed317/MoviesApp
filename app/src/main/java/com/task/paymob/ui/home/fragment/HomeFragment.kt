package com.task.paymob.ui.home.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private val favoriteMoviesList : MutableList<Movie> = mutableListOf()
    private val homeMoviesAdapter: HomeMoviesAdapter by lazy {
        HomeMoviesAdapter(::navigateToMovieDetailsFragment,favoriteMoviesList,::addFavoriteMovie,::deleteFavoriteMovie)
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
        getAllFavouriteMovies()
    }


    override fun initSetAdapter() {
        binding.moviesRv.adapter = homeMoviesAdapter
    }

    override fun initToolBar() {
        binding.toolbar.backBtn.visibility = View.GONE
        binding.toolbar.tvTitle.text = getString(R.string.home)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getMoviesForHome(year: String = "2024", sortBy: String = "", page : Int = 1) {
        DialogUtil.showDialog(requireActivity())
        homeViewModel.getMoviesForHome(year, sortBy, page)
        homeViewModel.responseGetMovies.observe(viewLifecycleOwner) {

            DialogUtil.dismissDialog()
            if (it.success != false) {
                homeMoviesAdapter.submitList(it.results)
                homeMoviesAdapter.notifyDataSetChanged()
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

    private fun getAllFavouriteMovies(){
        homeViewModel.getAllFavoriteMovies()
        homeViewModel.responseGetFavoriteMovies.observe(viewLifecycleOwner) {
            DialogUtil.dismissDialog()
            favoriteMoviesList.clear()
            favoriteMoviesList.addAll(it)
            getMoviesForHome()
        }
        homeViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
            DialogUtil.dismissDialog()
            binding.noResultsTv.visibility = View.VISIBLE
        }


    }




    private fun addFavoriteMovie(movie: Movie,imageView: ImageView){
        homeViewModel.addMovieToFavorite(movie)
        homeViewModel.responseAddedToFavorite.observe(viewLifecycleOwner) {
            favoriteMoviesList.add(movie)
            imageView.setImageResource(R.drawable.fav_icon_filled)
        }
        homeViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
            binding.noResultsTv.visibility = View.VISIBLE
        }


    }

    private fun deleteFavoriteMovie(movie: Movie,imageView: ImageView){
        homeViewModel.deleteFavoriteMovie(movie)
        homeViewModel.responseIsMovieDeleted.observe(viewLifecycleOwner) {
            favoriteMoviesList.remove(movie)
            imageView.setImageResource(R.drawable.fav_icon_unfilled)
        }
        homeViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
            binding.noResultsTv.visibility = View.VISIBLE
        }


    }


}