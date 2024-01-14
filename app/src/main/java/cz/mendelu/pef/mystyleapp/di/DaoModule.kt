package cz.mendelu.pef.mystyleapp.di

import cz.mendelu.pef.mystyleapp.cartDatabase.communication.CartDatabase
import cz.mendelu.pef.mystyleapp.cartDatabase.communication.ItemDao
import org.koin.dsl.module

val daoModule = module {

    fun provideDao(database: CartDatabase)
            : ItemDao = database.itemDao()

    single { provideDao(get()) }

}