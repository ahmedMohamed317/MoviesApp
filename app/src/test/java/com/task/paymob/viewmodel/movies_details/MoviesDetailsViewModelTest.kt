package com.task.paymob.viewmodel.movies_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.paymob.fake_data_source.FakeMoviesDetailsRepository
import com.task.paymob.model.Movie
import com.task.paymob.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest=Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MoviesDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesDetailsViewModel
    private lateinit var fakeRepository: FakeMoviesDetailsRepository

    @Before
    fun setUp() {
        fakeRepository = FakeMoviesDetailsRepository()
        viewModel = MoviesDetailsViewModel(fakeRepository, fakeRepository)
    }

    @Test
    fun `isThisMovieFavorite should return true if the movie is favorite`() = runTest {
        // Given
        val favoriteMovie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)
        val unfavouredMovie =Movie(false,"",2,"","Unfavoured Movie","",0.0,"","","",false,0.0,0)
        fakeRepository.favoriteMovies.add(favoriteMovie) // Adding a movie to the fake repo

        // When
        viewModel.isThisMovieFavorite(favoriteMovie.id)

        // Then
        assertEquals(true, viewModel.responseIsThisMovieFavorite.getOrAwaitValue()) // Assuming only one movie is added

        // When
        viewModel.isThisMovieFavorite(unfavouredMovie.id)

        // Then
        assertEquals(false, viewModel.responseIsThisMovieFavorite.getOrAwaitValue()) // Asserting the added movie
    }

    @Test
    fun `addMovieToFavorite should add a movie to the favorites`() = runTest {
        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)

        // When
        viewModel.addMovieToFavorite(movie)
        viewModel.isThisMovieFavorite(movie.id)

        // Then
        assertEquals(true, viewModel.responseAddedToFavorite.value)
        assertEquals(movie, fakeRepository.favoriteMovies[0])
        assertEquals(true, viewModel.responseIsThisMovieFavorite.value)

    }

    @Test
    fun `deleteFavoriteMovie should remove a movie from the favorites`() = runTest {
        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)
        fakeRepository.addMovieToFavorite(movie) // Adding a movie to the fake repo

        // When
        viewModel.deleteFavoriteMovie(movie)
        viewModel.isThisMovieFavorite(movie.id)

        // Then
        assertEquals(true, viewModel.responseIsMovieDeleted.getOrAwaitValue())
        assertTrue(fakeRepository.favoriteMovies.isEmpty())
        assertEquals(false, viewModel.responseIsThisMovieFavorite.getOrAwaitValue())

    }
}
