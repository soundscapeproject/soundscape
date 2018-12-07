package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.entity.RemoteSound
import com.example.dinhh.soundscape.data.local.SoundscapeLocalData
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeRepository {
    fun beginSearch(selectedCategory: String): Single<List<List<RemoteSound>>>

    fun saveLocalSoundScapes(localSoundscape: LocalSoundscape): Completable

    fun getAllLocalSoundScapes(): Single<List<LocalSoundscape>>

}

class SoundscapeRepositoryImpl(
    private val soundscapeLocalData: SoundscapeLocalData,
    private val soundscapeRemoteData: SoundscapeRemoteData,
    private val sharedPref: SharedPref): SoundscapeRepository {

    override fun saveLocalSoundScapes(localSoundscape: LocalSoundscape): Completable {
        return Completable.fromAction {
            soundscapeLocalData.saveLocalSoundscapes(localSoundscape)
        }
    }

    override fun getAllLocalSoundScapes(): Single<List<LocalSoundscape>> {
        return soundscapeLocalData.getAllLocalSoundscapes()
    }

    override fun beginSearch(selectedCategory: String): Single<List<List<RemoteSound>>> {
        return soundscapeRemoteData.fetchLibrary(key = sharedPref.getToken().blockingGet(),selectedCategory = selectedCategory)
    }
}