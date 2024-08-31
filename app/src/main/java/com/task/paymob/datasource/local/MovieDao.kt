package com.task.paymob.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.paymob.model.Movie


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getFavoriteMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToFavorite(movie: Movie) : Long

    @Delete
    suspend fun deleteMovieFromFavorite(movie: Movie) : Int

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :movieId)")
    suspend fun isThisMovieFavorite(movieId: Int): Boolean
}