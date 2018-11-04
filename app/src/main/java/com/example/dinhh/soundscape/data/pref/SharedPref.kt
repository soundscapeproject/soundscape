package com.example.dinhh.soundscape.data.pref

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

interface SharedPref {

    fun setLogInState(): Completable

    fun getLoginState(): Single<Boolean>

    fun clearLoginData(): Completable
}

class SharedPrefImpl(private val context: Context): SharedPref {

    val USER_PREFERENCES = "user_preferences"
    val KEY_LOG_IN = "log_in_key"
    val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun setLogInState(): Completable {
        preferences.edit()
            .putBoolean(KEY_LOG_IN, true)
            .apply()
        return Completable.complete()
    }

    override fun getLoginState(): Single<Boolean> {
        val loginState = preferences.getBoolean(KEY_LOG_IN, false)
        return Single.just(loginState)
    }

    override fun clearLoginData(): Completable {
        preferences.edit().remove(KEY_LOG_IN).apply()
        return Completable.complete()
    }
}