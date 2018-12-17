package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun login(username: String, password: String): Single<String>

    fun getToken(): Single<String>

    fun clearLoginData(): Completable
}

class UserRepositoryImpl(
    private val sharedPref: SharedPref,
    private val soundscapeRemoteData: SoundscapeRemoteData): UserRepository {

    override fun login(username: String, password: String): Single<String> {
        return soundscapeRemoteData.login(username, password)
            .flatMap {
                val token = it.apiKey
                if (token.equals("Incorrect credentials! Try again.")) {
                     Single.error(Throwable("Username or password is not correct"))
                } else {
                     Single.just(token)
                }
            }
            .doOnSuccess {
                sharedPref.setToken(it)
            }
    }

    override fun getToken(): Single<String> = sharedPref.getToken()

    override fun clearLoginData(): Completable = sharedPref.clearLoginData()
}