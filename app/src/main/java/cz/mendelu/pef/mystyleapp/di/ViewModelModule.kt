package cz.mendelu.pef.mystyleapp.di

import cz.mendelu.pef.mystyleapp.ui.screens.FirestoreViewModel
import cz.mendelu.pef.mystyleapp.ui.screens.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {  WelcomeViewModel(get()) }
    viewModel { FirestoreViewModel(get()) }
}