package cz.mendelu.pef.mystyleapp

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import cz.mendelu.pef.mystyleapp.di.daoModule
import cz.mendelu.pef.mystyleapp.di.databaseModule
import cz.mendelu.pef.mystyleapp.di.repositoryModule
import cz.mendelu.pef.mystyleapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@ToDoApplication)
            modules(listOf(
                // todo moduly
                viewModelModule,
                repositoryModule,
                daoModule,
                databaseModule

            ))

        }

    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}