package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException


interface Player {

    fun playSound(selectedSound: String): Completable

    fun stopSound(): Completable
}

class PlayerImpl: Player {

    private var mp: MediaPlayer = MediaPlayer()

    override fun playSound(selectedSound: String): Completable {
        return Completable.create {

            try {
                mp.reset()
                mp.setDataSource(selectedSound)
                mp.prepare()
                mp.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun stopSound(): Completable {
        return Completable.create {

            try {
                mp.stop()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }
}

