package com.example.dinhh.soundscape.data.pref

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

interface SharedPref {

    fun getToken(): Single<String>

    fun clearLoginData(): Completable

    fun setToken(token: String): Completable
}

class SharedPrefImpl(private val context: Context): SharedPref {

    val USER_PREFERENCES = "user_preferences"
    val KEY_LOG_IN = "log_in_key"
    val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun setToken(token: String): Completable {
        preferences.edit()
            .putString(KEY_LOG_IN, token)
            .apply()
        return Completable.complete()
    }

    override fun getToken(): Single<String> {
        val token = preferences.getString(KEY_LOG_IN, "")
        return Single.just(token)
    }

    override fun clearLoginData(): Completable {
        preferences.edit().remove(KEY_LOG_IN).apply()
        return Completable.complete()
    }
}