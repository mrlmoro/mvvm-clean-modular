package br.com.murilomoro.mvvmcleanmodular.di

import br.com.murilomoro.domain.interactor.GetPopularMoviesUseCase
import br.com.murilomoro.presentation.movie.MovieRecyclerAdapter
import br.com.murilomoro.presentation.movie.MovieListViewModel
import br.com.murilomoro.presentation.movie.favorite.FavoriteMovieListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    viewModel { MovieListViewModel(get(), get()) }

    viewModel { FavoriteMovieListViewModel(get()) }

    factory { MovieRecyclerAdapter() }

}