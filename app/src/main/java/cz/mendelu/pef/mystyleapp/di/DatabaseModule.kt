package cz.mendelu.pef.mystyleapp.di

import cz.mendelu.pef.mystyleapp.ToDoApplication
import cz.mendelu.pef.mystyleapp.cartDatabase.communication.CartDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(): CartDatabase
            = CartDatabase.getDatabase(ToDoApplication.appContext)

    single { provideDatabase() }

}