package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.RemoteSound
import com.example.dinhh.soundscape.data.entity.SoundType
import com.example.dinhh.soundscape.data.entity.Token
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface SoundscapeApi {

    @POST("api_auth/auth.php")
    fun login(@Body loginBody: LoginBody): Single<Token>


    @GET("api_audio_search/index.php/?")
    fun getSounds(
        @Query("key") key: String,
        @Query("category") category: String,
        @Query("link") link: Boolean):
            Single<List<List<RemoteSound>>>

    @Multipart
    @POST("api_upload/")
    fun uploadSound(
        @Query("key") key: String,
        @Query("resourcetype") resourcetype: Int,
        @Query("collection") collectionId: Int,
        @Query("field8") title: String,
        @Query("field75") category: String,
        @Query("field76") soundType: String,
        @Query("field79") length: Int,
        @Part file: MultipartBody.Part
    ): Completable

    @Multipart
    @POST("api_upload/")
    fun uploadSoundScape(
        @Query("key") key: String,
        @Query("resourcetype") resourcetype: Int,
        @Query("collection") collectionId: Int,
        @Query("field8") title: String,
        @Query("field75") category: String,
        @Query("field76") soundType: String,
        @Part file: MultipartBody.Part
    ): Completable
}

data class LoginBody(val username: String, val password: String)