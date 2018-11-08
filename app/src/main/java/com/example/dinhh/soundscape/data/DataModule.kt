package com.example.dinhh.soundscape.data

import android.arch.persistence.room.Room
import com.example.dinhh.soundscape.data.database.DatabaseConfig
import com.example.dinhh.soundscape.data.database.SoundScapeDb
import com.example.dinhh.soundscape.data.database.SoundscapeLocalData
import com.example.dinhh.soundscape.data.database.SoundscapeLocalDataImpl
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.pref.SharedPrefImpl
import com.example.dinhh.soundscape.data.remote.RemoteConfig
import com.example.dinhh.soundscape.data.remote.SoundscapeApi
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteDataImpl
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import com.example.dinhh.soundscape.data.repository.SoundscapeRepositoryImpl
import com.example.dinhh.soundscape.data.repository.UserRepository
import com.example.dinhh.soundscape.data.repository.UserRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    //Repository
    factory<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    factory<SoundscapeRepository> {
        SoundscapeRepositoryImpl(get())
    }

    //Remote
    factory<SoundscapeRemoteData> {
        SoundscapeRemoteDataImpl(get())
    }

    factory<SoundscapeApi> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(RemoteConfig.BASE_URL)
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

    // Local
    factory<SharedPref> {
        SharedPrefImpl(get())
    }

//    factory<SoundscapeLocalData> {
//        SoundscapeLocalDataImpl(get())
//    }
//
//    factory { get<SoundScapeDb>().soundScapeDao() }
//
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            SoundScapeDb::class.java,
//            DatabaseConfig.DATABASE_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}