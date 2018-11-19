package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.data.entity.Token
import com.example.dinhh.soundscape.device.PlaySound
import io.reactivex.Single

interface SoundscapeRemoteData {

    fun login(username: String, password: String): Single<Token>

    fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<Sound>>>
}

class SoundscapeRemoteDataImpl(private val soundScapeApi: SoundscapeApi): SoundscapeRemoteData {

    override fun login(username: String, password: String): Single<Token> {
       return soundScapeApi.login(LoginBody(username, password))
    }

    override fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<Sound>>> {
        return soundScapeApi.getSounds(key, selectedCategory, true)
    }
}