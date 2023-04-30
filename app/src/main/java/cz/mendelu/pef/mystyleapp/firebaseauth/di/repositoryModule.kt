package cz.mendelu.pef.mystyleapp.firebaseauth.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import cz.mendelu.pef.mystyleapp.firebaseauth.AuthRepository
import cz.mendelu.pef.mystyleapp.firebaseauth.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { FirebaseDatabase.getInstance().authDao() }
    factory<AuthRepository> { AuthRepositoryImpl(get()) }
}