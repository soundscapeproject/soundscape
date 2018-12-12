package com.example.dinhh.soundscape.data.remote

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Completable

class NetworkUnavailableException: Throwable("No connection")

interface NetworkService {

    fun hasNetWorkConnection(): Completable
}

class NetworkServiceImpl(private val context: Context): NetworkService {

    override fun hasNetWorkConnection(): Completable {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return Completable.create {
            if (networkInfo != null) {
                it.onComplete()
            } else {
                it.onError(NetworkUnavailableException())
            }
        }
    }
}