package com.example.dinhh.soundscape.data.repository

import org.koin.dsl.module.module

val repositoryModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
}