package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.Token
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SoundscapeApi {

    @POST("api_auth/auth.php")
    fun login(@Body loginBody: LoginBody): Single<Token>
}

data class LoginBody(val username: String, val password: String)