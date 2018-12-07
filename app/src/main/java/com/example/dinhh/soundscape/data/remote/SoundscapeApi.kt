package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.RemoteSound
import com.example.dinhh.soundscape.data.entity.Token
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SoundscapeApi {

    @POST("api_auth/auth.php")
    fun login(@Body loginBody: LoginBody): Single<Token>


    @GET("api_audio_search/index.php/?")
    fun getSounds(
        @Query("key") key: String,
        @Query("category") category: String,
        @Query("link") link: Boolean):
            Single<List<List<RemoteSound>>>
}

data class LoginBody(val username: String, val password: String)