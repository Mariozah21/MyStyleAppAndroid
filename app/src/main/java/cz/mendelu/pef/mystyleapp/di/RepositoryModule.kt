package cz.mendelu.pef.mystyleapp.di

import cz.mendelu.pef.mystyleapp.cartDatabase.communication.ItemDao
import cz.mendelu.pef.mystyleapp.cartDatabase.communication.ItemsRepository
import cz.mendelu.pef.mystyleapp.cartDatabase.communication.ItemsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideTasksRepository(dao: ItemDao): ItemsRepository {
        return ItemsRepositoryImpl(dao)
    }

    single { provideTasksRepository(get()) }


}