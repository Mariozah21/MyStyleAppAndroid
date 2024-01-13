package cz.mendelu.pef.mystyleapp.di

import cz.mendelu.pef.mystyleapp.architecture.ItemDao
import cz.mendelu.pef.mystyleapp.architecture.ItemsRepository
import cz.mendelu.pef.mystyleapp.architecture.ItemsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideTasksRepository(dao: ItemDao): ItemsRepository {
        return ItemsRepositoryImpl(dao)
    }

    single { provideTasksRepository(get()) }


}