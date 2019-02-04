package br.com.murilomoro.presentation.movie

import android.arch.lifecycle.MutableLiveData
import br.com.murilomoro.domain.exception.DefaultException
import br.com.murilomoro.domain.interactor.FavoriteMoviesUseCase
import br.com.murilomoro.domain.interactor.GetPopularMoviesUseCase
import br.com.murilomoro.domain.model.Movie
import br.com.murilomoro.presentation.ui.base.BaseViewModel
import br.com.murilomoro.presentation.utils.SingleLiveData

class MovieListViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : BaseViewModel() {

    sealed class State {
        object GoToFavorite : State()
    }

    private var currentPage: Int = 1

    val popularMovies = MutableLiveData<List<Movie>>()

    val error = SingleLiveData<DefaultException>()

    val state = SingleLiveData<State>()

    fun fetchPopularMovies() {
        subscribeSingle(
            observable = getPopularMoviesUseCase.getPopularMovies(currentPage)
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = {
                popularMovies.postValue(it)
            },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavoriteMovie(movie: Movie) {
        subscribeCompletable(
            observable = favoriteMoviesUseCase.updateFavoriteMovie(movie),
            complete = { fetchPopularMovies() },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavorite() {
        state.value = State.GoToFavorite
    }

}