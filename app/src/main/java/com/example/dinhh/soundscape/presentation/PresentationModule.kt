package com.example.dinhh.soundscape.presentation

import com.example.dinhh.soundscape.presentation.screens.LoginViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {
    viewModel {
        LoginViewModel(get())
    }
}