package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.*
import com.example.dinhh.soundscape.device.JsonWriter
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface SoundscapeRemoteData {

    fun login(username: String, password: String): Single<Token>

    fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>>

    fun uploadRecord(key: String, localRecord: LocalRecord): Completable

    fun uploadSoundScape(key: String, localSoundscape: LocalSoundscape): Completable
}

class SoundscapeRemoteDataImpl(
    private val networkService: NetworkService,
    private val jsonWriter: JsonWriter,
    private val soundScapeApi: SoundscapeApi
) : SoundscapeRemoteData {

    override fun login(username: String, password: String): Single<Token> {
        return networkService.hasNetWorkConnection().andThen(soundScapeApi.login(LoginBody(username, password)))
    }

    override fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>> {
        return networkService.hasNetWorkConnection().andThen(soundScapeApi.getSounds(key, selectedCategory, true))
    }

    override fun uploadRecord(key: String, localRecord: LocalRecord): Completable {

        val file = File(localRecord.url)

        val requestBody = RequestBody.create(MediaType.parse("audio/*"), file)
        val aFile = MultipartBody.Part.createFormData("userfile", file.name, requestBody)

        return networkService.hasNetWorkConnection().andThen(soundScapeApi.uploadSound(
            key,
            4,
            29,
            localRecord.title,
            localRecord.category.toLowerCase(),
            SoundType.EFFECTS.description.toLowerCase(),
            localRecord.length_sec.toInt(),
            aFile
        ))
    }

    override fun uploadSoundScape(key: String, localSoundscape: LocalSoundscape): Completable {

        val gson = Gson()
        val stringJson = gson.toJson(localSoundscape)

        return networkService.hasNetWorkConnection().andThen(jsonWriter.writeJson(stringJson)
            .flatMapCompletable { output -> uploadJson(key, output, localSoundscape) }
            .andThen(jsonWriter.deleteJsonFile()))
    }

    private fun uploadJson(key: String, output: String, localSoundscape: LocalSoundscape): Completable {

        val file = File(output)

        val requestBody = RequestBody.create(MediaType.parse(".json"), file)
        val aFile = MultipartBody.Part.createFormData("userfile", file.name, requestBody)

        return networkService.hasNetWorkConnection().andThen(soundScapeApi.uploadSoundScape(
            key,
            5,
            29,
            localSoundscape.title,
            SoundCategory.STORY.description.toLowerCase(),
            SoundType.SOUNDSCAPE.description.toLowerCase(),
            aFile
        ))
    }
}