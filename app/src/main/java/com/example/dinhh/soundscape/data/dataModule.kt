package com.example.dinhh.soundscape.data

import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.pref.SharedPrefImpl
import com.example.dinhh.soundscape.data.remote.SoundscapeApi
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteDataImpl
import com.example.dinhh.soundscape.data.repository.UserRepository
import com.example.dinhh.soundscape.data.repository.UserRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    //SharedPref
    factory<SharedPref> {
        SharedPrefImpl(get())
    }

    //Remote
    factory<SoundscapeRemoteData> {
        SoundscapeRemoteDataImpl(get())
    }

    factory<SoundscapeApi> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://resourcespace.tekniikanmuseo.fi/plugins/")
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                    .build()
            )
            .build()
            .create(SoundscapeApi::class.java)
    }
}