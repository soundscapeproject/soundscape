package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException


interface Player {

    fun playSound(selectedSound: String): Completable

    fun stopSound(): Completable

    fun onPlayComplete(): Completable
}

class PlayerImpl: Player {

    private lateinit var mediaPlayer: MediaPlayer

    override fun playSound(soundUrl: String): Completable {
        return Completable.create {

            mediaPlayer = MediaPlayer()

            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(soundUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun onPlayComplete(): Completable {

        return Completable.create {complete ->
            mediaPlayer.setOnCompletionListener {
                complete.onComplete()
            }
        }
    }

    override fun stopSound(): Completable {
        return Completable.create {
            try {
                mediaPlayer.stop()
                mediaPlayer.release()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }
}