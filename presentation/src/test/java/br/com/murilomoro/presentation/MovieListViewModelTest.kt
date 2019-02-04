package br.com.murilomoro.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import br.com.murilomoro.domain.exception.DefaultException
import br.com.murilomoro.domain.interactor.FavoriteMoviesUseCase
import br.com.murilomoro.domain.interactor.GetPopularMoviesUseCase
import br.com.murilomoro.domain.model.Movie
import br.com.murilomoro.presentation.movie.MovieListViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

/**
 * Created by Murilo Moro on 04/02/19.
 */
class MovieListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        private val MOCK_MOVIES = listOf(
            Movie(1, "title", 5, "/image.jpg", true)
        )
    }

    private val getPopularMoviesUseCaseMock = mock<GetPopularMoviesUseCase>()

    private val favoriteMoviesUseCaseMock = mock<FavoriteMoviesUseCase>()

    private val observerPopularMoviesMock: Observer<List<Movie>> = mock()

    private val observerErrorMock: Observer<DefaultException> = mock()

    lateinit var viewModel: MovieListViewModel

    @Before
    fun `Setup test`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = MovieListViewModel(
            getPopularMoviesUseCaseMock,
            favoriteMoviesUseCaseMock
        )
    }

    @Test
    fun `Test fetchPopularMovies() when success`() {
        //Prepare
        val subjectDelay = PublishSubject.create<List<Movie>>()
        `when`(getPopularMoviesUseCaseMock.getPopularMovies(1))
            .thenReturn(Single.just(MOCK_MOVIES).delaySubscription(subjectDelay))

        viewModel.popularMovies.observeForever(observerPopularMoviesMock)

        //Action
        viewModel.fetchPopularMovies()

        //Test
        `Test showLoading`(subjectDelay)
        verify(observerPopularMoviesMock).onChanged(MOCK_MOVIES)
    }

    @Test
    fun `Test fetchPopularMovies() when error`() {
        //Prepare
        val mockException = DefaultException("Mock error message")
        `when`(getPopularMoviesUseCaseMock.getPopularMovies(any()))
            .thenReturn(Single.error(mockException))

        viewModel.error.observeForever(observerErrorMock)

        //Action
        viewModel.fetchPopularMovies()

        //Test
        verify(observerErrorMock).onChanged(mockException)
    }

    private fun `Test showLoading`(subjectDelay: PublishSubject<List<Movie>>) {
        assertThat(viewModel.showLoading.get(), `is`(true))

        subjectDelay.onComplete()

        assertThat(viewModel.showLoading.get(), `is`(false))
    }

}