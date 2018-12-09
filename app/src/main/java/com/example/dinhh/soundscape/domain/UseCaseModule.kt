package com.example.dinhh.soundscape.domain


import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import com.example.dinhh.soundscape.domain.record.*
import com.example.dinhh.soundscape.domain.soundscape.*
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

    factory {
        DeleteRecordUseCase(get(), get())
    }

    factory {
        UploadRecordUseCase(get(), get())
    }

    //Library

    factory {
        BeginSearchUseCase(get(), get())
    }

    factory {
        PlaySoundUseCase(get(),get())
    }

    factory {
        StopSoundUseCase(get(),get())
    }

    //background_cardview

    factory {
        AddSoundScapeUseCase(get(),get())
    }

    factory {
        ClearSoundScapesUseCase(get(),get())
    }

    factory {
        PlaySingleSoundScapeUseCase(get(),get())
    }

    factory {
        SetLoopingUseCase(get())
    }

    factory {
        PlaySoundScapesUseCase(get(),get())
    }

    factory {
        StopSingleSoundScapeUseCase(get(),get())
    }

    factory {
        StopSoundsScapeUseCase(get(),get())
    }

    factory {
        GetSoundScapeUseCase(get(),get())
    }

    factory {
        RemoveSingleSoundScapeUseCase(get(),get())
    }

    factory {
        SaveSoundScapesUseCase(get(),get())
    }

    factory {
        GetLocalSoundscapesUseCase(get(),get())
    }

    factory {
        GetOneLocalSoundscapeUseCase(get(),get())
    }

    factory {
        AddAllSoundscapesUseCase(get(),get())
    }

    factory {
        UpdateLocalSoundScapeUseCase(get(),get())
    }

    factory {
        UploadSoundscapeUseCase(get(),get())
    }

    factory {
        DeleteSingleSoundScape(get(),get())
    }
}