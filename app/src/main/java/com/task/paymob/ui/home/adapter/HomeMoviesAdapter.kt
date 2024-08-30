package com.task.paymob.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.paymob.R
import com.task.paymob.base.DiffCallback
import com.task.paymob.databinding.ItemHomeMovieBinding
import com.task.paymob.model.Movie
import com.task.paymob.utils.Utils


class HomeMoviesAdapter(
    val navigateToMoviesDetails: (Movie) -> Unit,
    private val favoriteMoviesList: MutableList<Movie>,
    val addFavoriteMovie : (Movie, ImageView) -> Unit,
    val deleteFavoriteMovie: (Movie, ImageView) -> Unit
) : ListAdapter<Movie, HomeMoviesAdapter.ItemHomeMovieViewHolder>(DiffCallback<Movie>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeMovieViewHolder {
        val itemHomeMovieBinding = ItemHomeMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemHomeMovieViewHolder(itemHomeMovieBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ItemHomeMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        handleItemsDimension(holder)
    }

    inner class ItemHomeMovieViewHolder(
        private val binding: ItemHomeMovieBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieName.text = movie.title
            binding.movieName.isSelected = true
            handleFavoriteButtonView(binding.favoriteBtnIv,movie)
            binding.favoriteBtnIv.setOnClickListener {
                Log.d("TAG", "bind: ${movie.id}")
                handleFavoriteButtonClick(binding.favoriteBtnIv,movie)
            }
            binding.movieReleaseDateTv.text = movie.releaseDate
            if (movie.posterPath != null) {
                Utils.loadImage(
                    context,
                    movie.posterPath,
                    binding.moviePosterIv,
                    R.drawable.movie_placeholder,
                    8
                )
            } else {
                binding.moviePosterIv.setImageResource(R.drawable.movie_placeholder)
            }
            binding.ratingBar.rating = movie.voteAverage.toFloat() / 2

            binding.root.setOnClickListener {
                navigateToMoviesDetails(movie)
            }
        }
    }

    private fun handleItemsDimension(holder: ItemHomeMovieViewHolder) {
        val screenWidth = holder.itemView.context.resources.displayMetrics.widthPixels
        val itemWidth = (screenWidth / 2) - 98
        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = itemWidth
        holder.itemView.layoutParams = layoutParams
    }

    private fun handleFavoriteButtonView(button:ImageView,movie: Movie){
        if (movie.id in favoriteMoviesList.map { it.id }) {
            button.setImageResource(R.drawable.fav_icon_filled)
        } else {
            button.setImageResource(R.drawable.fav_icon_unfilled)
        }
    }
    private fun handleFavoriteButtonClick(button:ImageView,movie: Movie){
        if (movie.id in favoriteMoviesList.map { it.id }) {
            deleteFavoriteMovie(movie,button)
        } else {
            addFavoriteMovie(movie,button)
        }
    }
}
