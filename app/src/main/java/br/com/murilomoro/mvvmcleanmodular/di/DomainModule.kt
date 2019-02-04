package br.com.murilomoro.mvvmcleanmodular.di

import br.com.murilomoro.data.repository.MovieRepositoryImpl
import br.com.murilomoro.domain.interactor.FavoriteMoviesUseCase
import br.com.murilomoro.domain.interactor.GetPopularMoviesUseCase
import br.com.murilomoro.domain.repository.MovieRepository
import org.koin.dsl.module.module

val domainModule = module {

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    factory { GetPopularMoviesUseCase(get()) }

    factory { FavoriteMoviesUseCase(get()) }
}