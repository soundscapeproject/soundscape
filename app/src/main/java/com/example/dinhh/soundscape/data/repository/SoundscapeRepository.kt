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

    fun getOneLocalSoundScape(id: Long): Single<LocalSoundscape>

    fun updateLocalSoundScape(localSoundscape: LocalSoundscape): Completable

    fun uploadSoundScape(localSoundscape: LocalSoundscape): Completable

    fun deleteLocalSoundScape(localSoundscape: LocalSoundscape): Completable
}

class SoundscapeRepositoryImpl(
    private val soundscapeLocalData: SoundscapeLocalData,
    private val soundscapeRemoteData: SoundscapeRemoteData,
    private val sharedPref: SharedPref): SoundscapeRepository {

    override fun deleteLocalSoundScape(localSoundscape: LocalSoundscape): Completable {
        return soundscapeLocalData.deleteLocalSoundScape(localSoundscape)
    }

    override fun uploadSoundScape(localSoundscape: LocalSoundscape): Completable {
        return soundscapeRemoteData.uploadSoundScape(sharedPref.getToken().blockingGet(), localSoundscape)
    }

    override fun updateLocalSoundScape(localSoundscape: LocalSoundscape): Completable {
        return soundscapeLocalData.updateLocalSoundScape(localSoundscape)
    }

    override fun getOneLocalSoundScape(id: Long): Single<LocalSoundscape> {
        return soundscapeLocalData.getOneLocalSoundscape(id)
    }

    override fun saveLocalSoundScapes(localSoundscape: LocalSoundscape): Completable {
       return soundscapeLocalData.saveLocalSoundscapes(localSoundscape)
    }

    override fun getAllLocalSoundScapes(): Single<List<LocalSoundscape>> {
        return soundscapeLocalData.getAllLocalSoundscapes()
    }

    override fun beginSearch(selectedCategory: String): Single<List<List<RemoteSound>>> {
        return soundscapeRemoteData.fetchLibrary(key = sharedPref.getToken().blockingGet(),selectedCategory = selectedCategory)
    }
}