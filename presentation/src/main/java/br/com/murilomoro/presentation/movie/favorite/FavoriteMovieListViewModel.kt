package br.com.murilomoro.presentation.movie.favorite

import android.arch.lifecycle.MutableLiveData
import br.com.murilomoro.domain.exception.DefaultException
import br.com.murilomoro.domain.interactor.FavoriteMoviesUseCase
import br.com.murilomoro.domain.model.Movie
import br.com.murilomoro.presentation.ui.base.BaseViewModel
import br.com.murilomoro.presentation.utils.SingleLiveData


class FavoriteMovieListViewModel(
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : BaseViewModel() {

    val favoriteMovies = MutableLiveData<List<Movie>>()

    val error = SingleLiveData<DefaultException>()

    fun fetchFavoriteMovies() {
        subscribeSingle(
            observable = favoriteMoviesUseCase.getFavoriteMovies()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = { favoriteMovies.postValue(it) },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavoriteMovie(movie: Movie) {
        subscribeCompletable(
            observable = favoriteMoviesUseCase.updateFavoriteMovie(movie),
            complete = { fetchFavoriteMovies() },
            error = { error.postValue(it) }
        )
    }

}