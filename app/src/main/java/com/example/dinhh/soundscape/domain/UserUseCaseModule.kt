package com.example.dinhh.soundscape.domain

import org.koin.dsl.module.module

val userUseCaseModule = module {
    factory {
        LoginUseCase(get(), get())
    }

    factory {
        LogoutUseCase(get(), get())
    }

    factory {
        GetTokenUseCase(get(), get())
    }
}