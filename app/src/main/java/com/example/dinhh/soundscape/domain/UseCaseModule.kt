package com.example.dinhh.soundscape.domain

import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.record.PlayRecordUseCase
import com.example.dinhh.soundscape.domain.record.StartRecordUseCase
import com.example.dinhh.soundscape.domain.record.StopRecordUseCase
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
        BeginSearchUseCase(get(), get())
    }
}