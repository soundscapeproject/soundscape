package com.example.dinhh.soundscape.data.repository

import android.net.rtp.AudioStream
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import io.reactivex.Single

interface SoundscapeRepository {
    fun beginSearch(selectedCategory: String): Single<List<List<Sound>>>

}

class SoundscapeRepositoryImpl(private val soundscapeRemoteData: SoundscapeRemoteData, private val sharedPref: SharedPref): SoundscapeRepository {

    override fun beginSearch(selectedCategory: String): Single<List<List<Sound>>> {
        return soundscapeRemoteData.fetchLibrary(key = sharedPref.getToken().blockingGet(),selectedCategory = selectedCategory)
    }
}