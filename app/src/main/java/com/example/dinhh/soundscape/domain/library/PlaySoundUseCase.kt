package com.example.dinhh.soundscape.domain.library

import android.net.rtp.AudioStream
import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import com.example.dinhh.soundscape.device.PlaySound
import io.reactivex.Completable
import io.reactivex.Single

class PlaySoundUseCase (private val playSound: PlaySound, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedSound: String): Completable {
        return playSound.playSound(selectedSound)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}