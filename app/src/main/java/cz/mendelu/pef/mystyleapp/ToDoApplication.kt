package cz.mendelu.pef.mystyleapp

import android.app.Application
import android.content.Context
import cz.mendelu.pef.mystyleapp.firebaseauth.di.authRepoModule
import cz.mendelu.pef.mystyleapp.firebaseauth.di.databaseModule
import cz.mendelu.pef.mystyleapp.firebaseauth.di.mainViewModelModule
import cz.mendelu.pef.mystyleapp.firebaseauth.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(this@ToDoApplication)
            modules(listOf(
                // todo moduly
                authRepoModule,
                databaseModule,
                mainViewModelModule,
                repositoryModule
            ))

        }

    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}