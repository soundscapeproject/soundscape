package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException


interface Player {

    fun playSound(selectedSound: String, selectedPosition: Int): Completable

    fun stopSound(selectedPosition: Int): Completable
}

class PlayerImpl: Player {

    private var selectedMp: MutableMap<Int, MediaPlayer> = mutableMapOf()

    override fun playSound(selectedSound: String, selectedPosition: Int): Completable {
        return Completable.create {

            try {
                selectedMp[selectedPosition] = MediaPlayer()
                val thisMp = selectedMp[selectedPosition]
                thisMp!!.reset()
                thisMp.setDataSource(selectedSound)
                thisMp.prepare()
                thisMp.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun stopSound(selectedPosition: Int): Completable {
        return Completable.create {
            try {
                selectedMp[selectedPosition]!!.stop()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }
}

