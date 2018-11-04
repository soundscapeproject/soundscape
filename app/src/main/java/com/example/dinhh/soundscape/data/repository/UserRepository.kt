package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.entity.Token
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun login(username: String, password: String): Single<Token>

    fun setLoginState(): Completable

    fun getLoginState(): Single<Boolean>

    fun clearLoginData(): Completable
}

class UserRepositoryImpl(
    private val sharedPref: SharedPref,
    private val soundscapeRemoteData: SoundscapeRemoteData): UserRepository {

    override fun login(username: String, password: String): Single<Token> {
        return soundscapeRemoteData.login(username, password)
    }

    override fun setLoginState(): Completable = sharedPref.setLogInState()

    override fun getLoginState(): Single<Boolean> = sharedPref.getLoginState()

    override fun clearLoginData(): Completable = sharedPref.clearLoginData()
}