package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import android.util.Log
import io.reactivex.Completable
import java.io.IOException

interface PlaySound {
    fun playSound(selectedSound: String): Completable

    fun stopSound(): Completable
}

class PlaySoundImpl: PlaySound {
    private var mp = MediaPlayer()

    override fun playSound(selectedSound: String): Completable {
        return Completable.create {
                try {
                    mp.setDataSource(selectedSound)
                    mp.prepare()
                    mp.start()
                    it.onComplete()
                } catch (e: IOException) {
                    Log.e("Error: ", "prepare() failed")
                    it.onError(e)
                }
        }
    }

    override fun stopSound(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}