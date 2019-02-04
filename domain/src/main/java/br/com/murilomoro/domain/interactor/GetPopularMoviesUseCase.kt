package br.com.murilomoro.domain.interactor

import br.com.murilomoro.domain.model.Movie
import br.com.murilomoro.domain.repository.MovieRepository
import io.reactivex.Single

/**
 * Created by Murilo Moro on 31/01/19.
 */
class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    fun getPopularMovies(page: Int): Single<List<Movie>> {
        return movieRepository.getPopularMovies(page)
    }
}