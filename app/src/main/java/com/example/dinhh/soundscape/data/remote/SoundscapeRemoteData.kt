package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.data.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody



interface SoundscapeRemoteData {

    fun login(username: String, password: String): Single<Token>

    fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>>

    fun uploadRecord(key: String, localRecord: LocalRecord): Completable
}

class SoundscapeRemoteDataImpl(private val soundScapeApi: SoundscapeApi): SoundscapeRemoteData {

    override fun login(username: String, password: String): Single<Token> {
       return soundScapeApi.login(LoginBody(username, password))
    }

    override fun fetchLibrary(key: String, selectedCategory: String): Single<List<List<RemoteSound>>> {
        return soundScapeApi.getSounds(key, selectedCategory, true)
    }

    override fun uploadRecord(key: String, localRecord: LocalRecord): Completable {

        val file = File(localRecord.url)

        val requestBody = RequestBody.create(MediaType.parse("audio/*"), file)
        val aFile = MultipartBody.Part.createFormData("userfile", file.name, requestBody)

        return soundScapeApi.uploadSound(
            key,
            4,
            29,
            localRecord.title,
            localRecord.category.toLowerCase(),
            SoundType.EFFECTS.description.toLowerCase(),
            localRecord.length_sec.toInt(),
            aFile
        )
    }
}