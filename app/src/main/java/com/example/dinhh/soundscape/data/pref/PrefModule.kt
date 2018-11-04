package com.example.dinhh.soundscape.data.pref

import org.koin.dsl.module.module

val prefModule = module {
    factory<SharedPref> {
        SharedPrefImpl(get())
    }
}