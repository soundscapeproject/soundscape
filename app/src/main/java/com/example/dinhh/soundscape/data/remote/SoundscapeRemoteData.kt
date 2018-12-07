package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.RemoteSound
import com.example.dinhh.soundscape.data.entity.Token
import io.reactivex.Single

interface SoundscapeRemoteData {

    fun login(username: String, password: String): Single<Token>

    fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>>
}

class SoundscapeRemoteDataImpl(private val soundScapeApi: SoundscapeApi): SoundscapeRemoteData {

    override fun login(username: String, password: String): Single<Token> {
       return soundScapeApi.login(LoginBody(username, password))
    }

    override fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>> {
        return soundScapeApi.getSounds(key, selectedCategory, true)
    }
}