package com.task.paymob.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.paymob.R
import com.task.paymob.viewmodel.movies_details.MoviesDetailsViewModel
import com.task.paymob.base.BaseFragment
import com.task.paymob.databinding.FragmentMovieDetailsBinding
import com.task.paymob.utils.Utils
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
        binding.favoriteBtnIv.setOnClickListener {

        }
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

}

