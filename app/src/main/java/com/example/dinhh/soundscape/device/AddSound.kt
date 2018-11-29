package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import android.support.v7.widget.DialogTitle
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.entity.SoundscapeItem
import io.reactivex.Completable
import java.io.IOException

interface AddSound {
    fun addSound(selectedSound: String, selectedPosition: Int, title: String, length: Int): Completable
}

class AddSoundImpl: AddSound{

    private var selectedMp: MutableMap<Int, MediaPlayer> = mutableMapOf()

    override fun addSound(selectedSound: String, selectedPosition: Int, title: String, length: Int): Completable {
        return Completable.create {

            try {
                selectedMp[selectedPosition] = MediaPlayer()

                val thisMp = selectedMp[selectedPosition]
                thisMp!!.reset()
                thisMp.setDataSource(selectedSound)
                thisMp.prepare()

                val soundScapeItem = SoundscapeItem(title, length, thisMp)

                Model.selectedSounds.add(soundScapeItem)
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

}