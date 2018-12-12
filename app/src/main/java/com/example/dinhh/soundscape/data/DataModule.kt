package com.example.dinhh.soundscape.data

import android.arch.persistence.room.Room
import android.net.ConnectivityManager
import com.example.dinhh.soundscape.data.local.Database
import com.example.dinhh.soundscape.data.local.DatabaseConfig
import com.example.dinhh.soundscape.data.local.SoundscapeLocalData
import com.example.dinhh.soundscape.data.local.SoundscapeLocalDataImpl
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.pref.SharedPrefImpl
import com.example.dinhh.soundscape.data.remote.*
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import com.example.dinhh.soundscape.data.repository.SoundscapeRepositoryImpl
import com.example.dinhh.soundscape.data.repository.RecordRepository
import com.example.dinhh.soundscape.data.repository.RecordRepositoryImpl
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
    factory<UserRepository> {
        UserRepositoryImpl(get(), get())
    }


    factory<SoundscapeRepository> {
        SoundscapeRepositoryImpl(get(), get(), get())
    }

    factory<RecordRepository> {
        RecordRepositoryImpl(get(), get(), get())

    }

    //Remote
    factory<SoundscapeRemoteData> {
        SoundscapeRemoteDataImpl(get(), get(), get())
    }

    factory<NetworkService> {
        NetworkServiceImpl(get())
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

    //Local
    factory<SharedPref> {
        SharedPrefImpl(get())
    }

    factory<SoundscapeLocalData> {
        SoundscapeLocalDataImpl(get(), get())
    }

    factory { get<Database>().recordDao() }

    factory { get<Database>().soundscapeDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            DatabaseConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}