package cz.mendelu.pef.mystyleapp.firebaseauth.di

import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.google.firebase.database.FirebaseDatabase

val firebaseModule = module {
    single { FirebaseDatabase.getInstance().reference }
}
