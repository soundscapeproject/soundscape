package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.entity.SoundscapeItem
import io.reactivex.Completable
import java.io.IOException

interface AddSound {
    fun addSound(selectedSound: String, title: String, length: Int, category: String, volume: Int): Completable
}

class AddSoundImpl: AddSound{

    override fun addSound(selectedSound: String, title: String, length: Int, category: String, volume: Int): Completable {
        return Completable.create {

            try {

                val thisMp = MediaPlayer()
                thisMp!!.reset()
                thisMp.setDataSource(selectedSound)
                thisMp.prepare()

                val soundScapeItem = SoundscapeItem(title, length, thisMp, category, volume)

                Model.selectedSounds.add(soundScapeItem)
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

}