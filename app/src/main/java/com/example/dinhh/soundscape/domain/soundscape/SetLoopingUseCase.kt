package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class SetLoopingUseCase(private val soundScape: SoundScape) {

    fun execute(index: Int, isLooping: Boolean): Completable {
        return soundScape.loopSingleSound(index, isLooping)
    }
}