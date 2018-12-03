package com.example.dinhh.soundscape.domain


import com.example.dinhh.soundscape.domain.library.AddSelectedSoundUseCase
import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import com.example.dinhh.soundscape.domain.record.StartRecordUseCase
import com.example.dinhh.soundscape.domain.record.StopRecordUseCase
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
        StartRecordUseCase(get(), get())
    }

    factory {
        StopRecordUseCase(get(), get())
    }

    factory {
        SaveRecordUseCase(get(), get(), get())
    }

    factory {
        GetRecordsUseCase(get(), get())
    }

    factory {
        DeleteTempRecordUseCase(get(), get())
    }


    //Library

    factory {
        BeginSearchUseCase(get(), get())
    }

    //Play sound
    factory {
        PlaySoundUseCase(get(),get())
    }

    factory {
        StopSoundUseCase(get(),get())
    }

    //Add Sound
    factory {
        AddSelectedSoundUseCase(get(),get())
    }
}