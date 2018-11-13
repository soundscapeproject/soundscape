package com.example.dinhh.soundscape.device

import android.util.Log
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeApi
import com.example.dinhh.soundscape.presentation.ListItem
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import java.io.IOException

interface Library {
    fun beginSearch(selectedCategory: String): Completable
}

class LibraryImpl(private val sharedPref: SharedPref): Library{
    override fun beginSearch(selectedCategory: String): Completable {
        return Completable.create {
            Model.sounds.clear()
            val apiKey = sharedPref.getToken()
            try {
                disposable =
                        wikiApiServe.getSounds(
                            key = apiKey.blockingGet(),
                            category = selectedCategory
                        ).subscribe(
                                { result ->
                                    for (i in result.indices) {
                                        Model.sounds.add(ListItem(result[i][0].title))
                                    }
                                    it.onComplete()
                                }
                                ,
                                { error -> Log.d("Error: ", error.message) }

                            )
            }catch (e: IOException){
                it.onError(e)
            }
            Completable.complete()
        }

    }

    lateinit var disposable: Disposable

    private val wikiApiServe by lazy {
        SoundscapeApi.create()
    }
}