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
//    init observers
    override fun initViewModel() {
        observeMovies()
        observeFavoriteMovies()
        observeErrors()
    }

    override fun onCreateInit() {
        getAllFavoriteMovies()
    }
    // init adapter
    override fun initSetAdapter() {
        binding.moviesRv.adapter = homeMoviesAdapter
        setupPagination()
    }
    // init toolbar
    override fun initToolBar() {
        binding.toolbar.backBtn.visibility = View.GONE
        binding.toolbar.tvTitle.text = getString(R.string.home)
    }
    // getting movies from api of 2024 year and sorting is default
    private fun getMoviesForHome(year: String = "2024", sortBy: String = "") {
        showLoadingDialog()
        homeViewModel.getMoviesForHome(year, sortBy, homeViewModel.currentPage)
    }

    private fun getAllFavoriteMovies() {
        homeViewModel.getAllFavoriteMovies()
    }
    // navigating to movie detail fragment if clicking on a movie
    private fun navigateToMovieDetailsFragment(movie: Movie) {
        if (checkCurrentDestination(R.id.homeFragment)) {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie)
            findNavController().navigate(action)
        }
    }
    // adding favorite movie from home
    private fun addFavoriteMovie(movie: Movie, imageView: ImageView) {
        homeViewModel.addMovieToFavorite(movie)
        observeFavoriteActionSuccess(movie, imageView, true)
    }
    // deleting favorite movie from home
    private fun deleteFavoriteMovie(movie: Movie, imageView: ImageView) {
        homeViewModel.deleteFavoriteMovie(movie)
        observeFavoriteActionSuccess(movie, imageView, false)
    }
    // listening to the recycler view so if scrolled till the end of the page it loads more data for the api
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
    // observing the api of getting movies so if the it succeeded and the page is one then submitting the list , but if succeded and page is
    // bigger than one then it does pagination and adding the new items to the list
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
    // getting favorite movies list from database then calling the api to get all the movies to show the movies that are favorite and that aren't
    private fun observeFavoriteMovies() {
        homeViewModel.responseGetFavoriteMovies.observe(viewLifecycleOwner) {
            homeViewModel.favoriteMoviesList.clear()
            homeViewModel.favoriteMoviesList.addAll(it)
            getMoviesForHome()
        }
    }
    // observing the adding or deleting favorite items success so if adding succeeded then it should add the item to favorite list
    // and if deleting it should remove it from the list and show the icon indicates deletion
    private fun observeFavoriteActionSuccess(movie: Movie, imageView: ImageView, isAdded: Boolean) {
        if (isAdded) {
            homeViewModel.responseAddedToFavorite.observe(viewLifecycleOwner) { success ->
                if (success) {
                    homeViewModel.favoriteMoviesList.add(movie)
                    imageView.setImageResource(R.drawable.fav_icon_filled)
                    homeViewModel.responseAddedToFavorite.removeObservers(viewLifecycleOwner)
                }
            }
        } else {
            homeViewModel.responseIsMovieDeleted.observe(viewLifecycleOwner) { success ->
                if (success) {
                    homeViewModel.favoriteMoviesList.remove(movie)
                    imageView.setImageResource(R.drawable.fav_icon_unfilled)
                    homeViewModel.responseIsMovieDeleted.removeObservers(viewLifecycleOwner)
                }
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
    // handles error and dismiss loading dialog
    private fun handleError(message: String) {
        showSnackbar(message)
        dismissLoadingDialog()
        handleViewsVisibility(shouldViewRecycler = false)
    }

    // handles if there is result for data then it should show rv and hide no result tv
    // but if no result then vice versa depending of the boolean value
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
