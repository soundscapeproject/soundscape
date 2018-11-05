package com.example.dinhh.soundscape.common

import com.example.dinhh.soundscape.domain.LoginUseCase
import org.koin.dsl.module.module

val schedulerModule = module {
    factory<SchedulerProvider> {
        SchedulerProviderImpl()
    }
}