package com.task.paymob.repository.movieDetails

import com.task.paymob.fake_data_source.FakeMovieDao
import com.task.paymob.model.Movie
import com.task.paymob.repository.shared_repo.SharedRepositoryImpl
import com.task.paymob.utils.api.AppResult
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailsRepositoryImplTest {

    private lateinit var fakeMovieDao: FakeMovieDao
    private lateinit var movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl

    @Before
    fun setUp() {
        fakeMovieDao = FakeMovieDao()
        movieDetailsRepositoryImpl = MovieDetailsRepositoryImpl(fakeMovieDao)
    }
//    adding a movie to favorite and checking if it was added by the method isThisMovieFavorite
    @Test
    fun `isThisMovieFavorite should return true if the movie is favorite`() = runTest {
        // Given
        val movie = Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)

        // When
        fakeMovieDao.addMovieToFavorite(movie)
        val result = movieDetailsRepositoryImpl.isThisMovieFavorite(movie.id)

        // Then

        assertTrue((result as AppResult.Success).successData ) //    should return true if the movie is favorite

}
}