package br.com.murilomoro.mvvmcleanmodular.di

import br.com.murilomoro.data.local.db.AppDatabase
import br.com.murilomoro.data.remote.MovieService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {

    single { AppDatabase.createDatabase(androidApplication()) }

    single { get<AppDatabase>().movieDao() }

    single { MovieService.createMovieService() }
}