package com.task.paymob.viewmodel.home
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.paymob.fake_data_source.FakeHomeRepository
import com.task.paymob.model.Movie
import com.task.paymob.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeRepository: FakeHomeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeHomeRepository()
        viewModel = HomeViewModel(fakeRepository, fakeRepository)
    }

    @Test
    fun `getMoviesForHome should return a list of movies`() = runTest {
        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)
        fakeRepository.apiMovies.add(movie) // Adding a movie to the fake repo

        // When
        viewModel.getMoviesForHome(page = 1)

        // Then
        assertEquals(1, viewModel.responseGetMovies.getOrAwaitValue()?.results?.size) // Assuming only one movie is added
        assertEquals(movie, viewModel.responseGetMovies.getOrAwaitValue()?.results?.get(0)) // Asserting the added movie
    }

    @Test
    fun `addMovieToFavorite should add a movie to the favorites`() = runTest {
        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)

        // When
        viewModel.addMovieToFavorite(movie)

        // Then
        assertEquals(true, viewModel.responseAddedToFavorite.getOrAwaitValue())
        assertEquals(movie, fakeRepository.favoriteMovies[0])
    }

    @Test
    fun `deleteFavoriteMovie should remove a movie from the favorites`() = runTest {
        // Given
        val movie =Movie(false,"",1,"","Movie","",0.0,"","","",false,0.0,0)
        fakeRepository.addMovieToFavorite(movie)

        // When
        viewModel.deleteFavoriteMovie(movie)

        // Then
        assertEquals(true, viewModel.responseIsMovieDeleted.getOrAwaitValue ())
        assertTrue(fakeRepository.favoriteMovies.isEmpty())
    }
}
