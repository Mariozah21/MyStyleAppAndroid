package cz.mendelu.pef.mystyleapp.di

import android.content.ClipData.Item
import cz.mendelu.pef.mystyleapp.architecture.CartDatabase
import cz.mendelu.pef.mystyleapp.architecture.ItemDao
import org.koin.dsl.module

val daoModule = module {

    fun provideDao(database: CartDatabase)
            : ItemDao = database.itemDao()

    single { provideDao(get()) }

}