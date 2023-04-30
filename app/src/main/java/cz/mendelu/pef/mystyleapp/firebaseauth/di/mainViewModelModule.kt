package cz.mendelu.pef.mystyleapp.firebaseauth.di

import cz.mendelu.pef.mystyleapp.ui.screens.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainViewModelModule = module {
    viewModel { MainViewModel(get()) }
}