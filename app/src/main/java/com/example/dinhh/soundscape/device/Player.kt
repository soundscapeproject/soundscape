package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException

interface PlaySound {
    fun playSound(selectedSound: String): Completable
}

interface StopSound {
    fun stopSound(): Completable
}

object MediaPlayerImpl {
    private var mp: MediaPlayer = MediaPlayer()
    val instance: MediaPlayer
        get() {
            return mp
        }
}

class PlaySoundImpl: PlaySound {
    val mp = MediaPlayerImpl.instance

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
}

class StopSoundImpl: StopSound {
    val mp = MediaPlayerImpl.instance

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