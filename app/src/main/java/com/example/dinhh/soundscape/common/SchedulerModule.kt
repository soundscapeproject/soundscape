package com.example.dinhh.soundscape.common

import org.koin.dsl.module.module

val schedulerModule = module {
    factory<SchedulerProvider> {
        SchedulerProviderImpl()
    }
}