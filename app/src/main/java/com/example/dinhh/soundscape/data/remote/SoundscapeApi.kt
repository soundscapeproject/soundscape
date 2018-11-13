package com.example.dinhh.soundscape.data.remote

import com.example.dinhh.soundscape.data.entity.Token
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SoundscapeApi {

    @POST("api_auth/auth.php")
    fun login(@Body loginBody: LoginBody): Single<Token>


    @GET("api_audio_search/index.php/?")
    fun getSounds(@Query("key") key: String,
                  @Query("category") category: String):
            Single<List<List<SoundNetworkModel.Response>>>

    companion object {
        fun create(): SoundscapeApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://resourcespace.tekniikanmuseo.fi/plugins/")
                .build()

            return retrofit.create(SoundscapeApi::class.java)
        }
    }
}

data class LoginBody(val username: String, val password: String)