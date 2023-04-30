package cz.mendelu.pef.mystyleapp.firebaseauth.di

import cz.mendelu.pef.mystyleapp.firebaseauth.AuthRepository
import cz.mendelu.pef.mystyleapp.firebaseauth.AuthRepositoryImpl
import org.koin.dsl.module

val authRepoModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}