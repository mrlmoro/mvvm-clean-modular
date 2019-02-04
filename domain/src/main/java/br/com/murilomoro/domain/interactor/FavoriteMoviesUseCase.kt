package br.com.murilomoro.domain.interactor

import br.com.murilomoro.domain.model.Movie
import br.com.murilomoro.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    fun updateFavoriteMovie(movie: Movie): Completable {
        return if (movie.favorite) {
            deleteFavoriteMovie(movie)
        } else {
            saveFavoriteMovie(movie)
        }
    }

    fun saveFavoriteMovie(movie: Movie): Completable {
        return movieRepository.saveFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: Movie): Completable {
        return movieRepository.deleteFavoriteMovie(movie.id)
    }

    fun getFavoriteMovies(): Single<List<Movie>> {
        return movieRepository.getFavoritesMovie()
    }
}