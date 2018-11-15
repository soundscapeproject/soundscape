package com.example.dinhh.soundscape.domain

import com.example.dinhh.soundscape.domain.record.*
import com.example.dinhh.soundscape.domain.user.GetTokenUseCase
import com.example.dinhh.soundscape.domain.user.LoginUseCase
import com.example.dinhh.soundscape.domain.user.LogoutUseCase
import org.koin.dsl.module.module

val useCaseModule = module {

    //USER
    factory {
        LoginUseCase(get(), get())
    }

    factory {
        LogoutUseCase(get(), get())
    }

    factory {
        GetTokenUseCase(get(), get())
    }

    //RECORD

    factory {
        PlayRecordUseCase(get(), get())
    }

    factory {
        StartRecordUseCase(get(), get())
    }

    factory {
        StopRecordUseCase(get(), get())
    }

    factory {
        SaveRecordUseCase(get(), get())
    }

    factory {
        GetRecordsUseCase(get(), get())
    }

    factory {
        DeleteTempRecordUseCase(get(), get())
    }
}