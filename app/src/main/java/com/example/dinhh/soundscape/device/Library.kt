package com.example.dinhh.soundscape.device

import android.util.Log
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.remote.SoundscapeApi
import com.example.dinhh.soundscape.presentation.ListItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

interface Library {
    fun beginSearch(selectedCategory: String): Completable
}

class LibraryImpl: Library{
    override fun beginSearch(selectedCategory: String): Completable {
        return Completable.create {
            Model.sounds.clear()
            val apiKey =
                "jFBaDxPcNzYZGu-gNMZ2L9-TjP1JjWl8OHFhdJV54gL82_M0cZi8oGEg-fB7gw3EpYvN0IHrHFP-Ic5sULo-iAWTl0k_y0t3CwrCQPpbYJkVIjmCV1Zzo0NB52ZLanwN"
            try {
                disposable =
                        wikiApiServe.getSounds(
                            key = apiKey,
                            category = selectedCategory
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { result ->
                                    for (i in result.indices) {
                                        Model.sounds.add(ListItem(result[i][0].Title))
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