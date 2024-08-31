package com.task.paymob.repository.shared_repo

import com.task.paymob.fake_data_source.FakeMovieDao
import com.task.paymob.model.Movie
import com.task.paymob.utils.api.AppResult
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SharedRepositoryImplTest {

    private lateinit var fakeMovieDao: FakeMovieDao
    private lateinit var sharedRepository: SharedRepositoryImpl

    @Before
    fun setUp() {
        fakeMovieDao = FakeMovieDao()
        sharedRepository = SharedRepositoryImpl(fakeMovieDao)
    }
    // adding fake movie to favorite and check the return if it is true and also check by the method isThisMovieFavorite
    @Test
    fun `addMovieToFavorite should return success when movie is added successfully`() = runTest {
        // Given

        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)
        // When

        val result = sharedRepository.addMovieToFavorite(movie)
        // Then

        assertEquals(AppResult.Success(true), result)
        assertTrue(fakeMovieDao.isThisMovieFavorite(movie.id))
    }


    // deleting fake movie from favorite and check the return if it is true and also check by the method isThisMovieFavorite
    // by using assert false as the movie should not be in the favorite list
    @Test
    fun `deleteMovieFromFavorite should return success when movie is deleted successfully`() = runTest {

        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)

        // When
        sharedRepository.addMovieToFavorite(movie)
        val result = sharedRepository.deleteMovieFromFavorite(movie)

        // Then
        assertEquals(true, (result as AppResult.Success).successData)
        assertFalse(fakeMovieDao.isThisMovieFavorite(movie.id))
    }


}
