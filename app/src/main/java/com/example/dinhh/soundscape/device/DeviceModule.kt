package com.example.dinhh.soundscape.device

import org.koin.dsl.module.module

val deviceModule = module {

    single<Record> {
        RecordImpl()
    }

    single<Player> {
        PlayerImpl()
    }

    single<SoundScape> {
        SoundScapeImpl()
    }
}

