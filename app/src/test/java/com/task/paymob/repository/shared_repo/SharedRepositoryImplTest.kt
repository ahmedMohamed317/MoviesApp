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
