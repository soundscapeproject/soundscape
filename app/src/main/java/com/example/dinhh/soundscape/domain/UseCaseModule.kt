package com.example.dinhh.soundscape.domain

import org.koin.dsl.module.module

val useCaseModule = module {
    factory {
        LoginUseCase(get(), get())
    }
}