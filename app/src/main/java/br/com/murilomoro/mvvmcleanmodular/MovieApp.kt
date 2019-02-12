package br.com.murilomoro.mvvmcleanmodular

import android.app.Application
import br.com.murilomoro.mvvmcleanmodular.di.appModule
import br.com.murilomoro.mvvmcleanmodular.di.dataModule
import br.com.murilomoro.mvvmcleanmodular.di.domainModule
import br.com.murilomoro.mvvmcleanmodular.di.presentationModule
import org.koin.android.ext.android.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            androidContext = this,
            modules = listOf(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            ),
            loadPropertiesFromFile = true
        )
    }
}