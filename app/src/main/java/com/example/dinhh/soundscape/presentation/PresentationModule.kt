package com.example.dinhh.soundscape.presentation

import com.example.dinhh.soundscape.presentation.screens.login.LoginViewModel
import com.example.dinhh.soundscape.presentation.screens.main.MainViewModel
import com.example.dinhh.soundscape.presentation.screens.record.RecordViewModel
import com.example.dinhh.soundscape.presentation.screens.savedrecord.SavedRecordViewModel
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundViewModel
import com.example.dinhh.soundscape.presentation.screens.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        RecordViewModel(get(), get(), get(), get(), get())
    }

    viewModel {
        SoundViewModel(get(), get(), get())
    }

    viewModel {
        SavedRecordViewModel(get())
    }
}