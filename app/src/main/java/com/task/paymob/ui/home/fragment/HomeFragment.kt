package com.task.paymob.ui.home.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.paymob.R
import com.task.paymob.viewmodel.home.HomeViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentHomeBinding
import com.task.paymob.model.Movie
import com.task.paymob.ui.home.adapter.HomeMoviesAdapter
import com.task.paymob.utils.app.DialogUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModel<HomeViewModel>()
    private val homeMoviesAdapter: HomeMoviesAdapter by lazy {
        HomeMoviesAdapter(::navigateToMovieDetailsFragment, homeViewModel.favoriteMoviesList, ::addFavoriteMovie, ::deleteFavoriteMovie)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {
        observeMovies()
        observeFavoriteMovies()
        observeErrors()
    }

    override fun onCreateInit() {
        getAllFavoriteMovies()
    }

    override fun initSetAdapter() {
        binding.moviesRv.adapter = homeMoviesAdapter
        setupPagination()
    }

    override fun initToolBar() {
        binding.toolbar.backBtn.visibility = View.GONE
        binding.toolbar.tvTitle.text = getString(R.string.home)
    }

    private fun getMoviesForHome(year: String = "2024", sortBy: String = "") {
        showLoadingDialog()
        homeViewModel.getMoviesForHome(year, sortBy, homeViewModel.currentPage)
    }

    private fun getAllFavoriteMovies() {
        homeViewModel.getAllFavoriteMovies()
    }

    private fun navigateToMovieDetailsFragment(movie: Movie) {
        if (checkCurrentDestination(R.id.homeFragment)) {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie)
            findNavController().navigate(action)
        }
    }

    private fun addFavoriteMovie(movie: Movie, imageView: ImageView) {
        homeViewModel.addMovieToFavorite(movie)
        observeFavoriteActionSuccess(movie, imageView, true)
    }

    private fun deleteFavoriteMovie(movie: Movie, imageView: ImageView) {
        homeViewModel.deleteFavoriteMovie(movie)
        observeFavoriteActionSuccess(movie, imageView, false)
    }

    private fun setupPagination() {
        binding.moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= (lastVisibleItemPosition + 6)) {
                    homeViewModel.currentPage++
                    getMoviesForHome()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeMovies() {
        homeViewModel.responseGetMovies.observe(viewLifecycleOwner) {
            dismissLoadingDialog()
            if (it.success != false) {
                homeViewModel.moviesList.addAll(it.results)
                if (homeViewModel.currentPage == 1) {
                    homeMoviesAdapter.submitList(homeViewModel.moviesList)
                }
                homeMoviesAdapter.notifyDataSetChanged()
                handleViewsVisibility(shouldViewRecycler = true)
            } else {
                it.message?.let { it1 -> handleError(it1) }
            }
        }
    }

    private fun observeFavoriteMovies() {
        homeViewModel.responseGetFavoriteMovies.observe(viewLifecycleOwner) {
            homeViewModel.favoriteMoviesList.clear()
            homeViewModel.favoriteMoviesList.addAll(it)
            getMoviesForHome()
        }
    }

    private fun observeFavoriteActionSuccess(movie: Movie, imageView: ImageView, isAdded: Boolean) {
        val observer = if (isAdded) homeViewModel.responseAddedToFavorite else homeViewModel.responseIsMovieDeleted
        observer.observe(viewLifecycleOwner) {
            if (isAdded) {
                homeViewModel.favoriteMoviesList.add(movie)
                imageView.setImageResource(R.drawable.fav_icon_filled)
            } else {
                homeViewModel.favoriteMoviesList.remove(movie)
                imageView.setImageResource(R.drawable.fav_icon_unfilled)
            }
        }
    }

    private fun observeErrors() {
        homeViewModel.showError.observe(viewLifecycleOwner) {
            if (it != null) {

                handleError(it)
            }
        }
    }

    private fun showLoadingDialog() {
        DialogUtil.showDialog(requireActivity())
    }

    private fun dismissLoadingDialog() {
        DialogUtil.dismissDialog()
    }

    private fun handleError(message: String) {
        showSnackbar(message)
        dismissLoadingDialog()
        handleViewsVisibility(shouldViewRecycler = false)
    }


    private fun handleViewsVisibility(shouldViewRecycler :Boolean) {
        if (shouldViewRecycler) {
            binding.noResultsTv.visibility = View.GONE
            binding.moviesRv.visibility = View.VISIBLE
        }
        else{
            binding.noResultsTv.visibility = View.VISIBLE
            binding.moviesRv.visibility = View.GONE
        }
    }
}
