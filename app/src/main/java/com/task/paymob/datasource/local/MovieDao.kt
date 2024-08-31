package com.task.paymob.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.paymob.model.Movie


@Dao
interface MovieDao {
    // get all favorite movies
    @Query("SELECT * FROM movies")
    suspend fun getFavoriteMovies(): List<Movie>
    // add movie to favorite which returns long which indicates the row the movie is added to
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToFavorite(movie: Movie) : Long
    // delete movie from favorite which returns int 1 if deleted and  0 if not deleted
    @Delete
    suspend fun deleteMovieFromFavorite(movie: Movie) : Int
    // check if movie is favorite and return true if it's favorite and false if not
    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :movieId)")
    suspend fun isThisMovieFavorite(movieId: Int): Boolean
}