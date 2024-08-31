package com.task.paymob.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.paymob.R
import com.task.paymob.viewmodel.movies_details.MoviesDetailsViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentMovieDetailsBinding
import com.task.paymob.model.Movie
import com.task.paymob.utils.app.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val moviesDetailsViewModel by viewModel<MoviesDetailsViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater , container , false)


    override fun initClicks() {
        binding.myToolbar.backBtn.setOnClickListener {
            navigateUp()
        }
    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
        isTheMovieFavorite(args.movie)
        setupMovieData()
    }

    override fun initSetAdapter() {
    }

    override fun initToolBar() {
        binding.myToolbar.tvTitle.text =resources.getString( R.string.movie_details)
    }
    private fun navigateUp(){
        findNavController().navigateUp()
    }

    private fun setupMovieData(){
        binding.movieNameTv.text = args.movie.title
        binding.movieNameTv.isSelected = true
        binding.releaseDateTv.text = args.movie.releaseDate
        if (args.movie.posterPath != null) {
            Utils.loadImage(
                requireContext(),
                args.movie.posterPath.toString(),
                binding.moviePosterIv,
                R.drawable.movie_placeholder,
                16
            )
        } else {
            binding.moviePosterIv.setImageResource(R.drawable.movie_placeholder)
        }
        binding.voteAverageTv.text = getString(R.string.vote_average_format, args.movie.voteAverage)
        binding.productDescriptionTextTv.text = args.movie.overview
        binding.languageTv.text = getString(R.string.language_format , args.movie.originalLanguage)

    }

    private fun addFavoriteMovie(){
        moviesDetailsViewModel.addMovieToFavorite(args.movie)
        moviesDetailsViewModel.responseAddedToFavorite.observe(viewLifecycleOwner) {
            binding.favoriteBtnIv.setImageResource(R.drawable.fav_icon_filled)
        }
        moviesDetailsViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }


    }

    private fun deleteFavoriteMovie(){
        moviesDetailsViewModel.deleteFavoriteMovie(args.movie)
        moviesDetailsViewModel.responseIsMovieDeleted.observe(viewLifecycleOwner) {
            binding.favoriteBtnIv.setImageResource(R.drawable.fav_icon_unfilled)
        }
        moviesDetailsViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }


    }

    private fun isTheMovieFavorite(movie: Movie){
        moviesDetailsViewModel.isThisMovieFavorite(movie.id)
        moviesDetailsViewModel.responseIsThisMovieFavorite.observe(viewLifecycleOwner) { isFavorite ->
            binding.favoriteBtnIv.visibility = View.VISIBLE
            binding.favoriteBtnIv.setOnClickListener {
                handleFavoriteButtonClick(isFavorite)
            }
            handleFavoriteButtonView(isFavorite)
        }
        moviesDetailsViewModel.showError.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }


    }

    private fun handleFavoriteButtonView(isFavorite: Boolean){
        if (isFavorite) {
            binding.favoriteBtnIv.setImageResource(R.drawable.fav_icon_filled)
        } else {
            binding.favoriteBtnIv.setImageResource(R.drawable.fav_icon_unfilled)
        }
    }
    private fun handleFavoriteButtonClick(isFavorite: Boolean){
        if (isFavorite) {
            deleteFavoriteMovie()
        } else {
            addFavoriteMovie()
        }
    }
}

