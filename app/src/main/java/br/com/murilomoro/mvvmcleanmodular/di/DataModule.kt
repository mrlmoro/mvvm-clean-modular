package br.com.murilomoro.mvvmcleanmodular.di

import br.com.murilomoro.data.local.db.AppDatabase
import br.com.murilomoro.data.remote.MovieService
import br.com.murilomoro.data.remote.interceptor.RemoteRequestInterceptor
import br.com.murilomoro.data.remote.interceptor.RxRemoteErrorInterceptor
import br.com.murilomoro.mvvmcleanmodular.R
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {

    factory { RxRemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor(getProperty("api_key")) }

    single {
        AppDatabase.createDatabase(
            androidApplication(),
            getProperty("database_name")
        )
    }

    single { get<AppDatabase>().movieDao() }

    single {
        MovieService.createMovieService(
            getProperty("base_url"),
            get(),
            get()
        )
    }
}