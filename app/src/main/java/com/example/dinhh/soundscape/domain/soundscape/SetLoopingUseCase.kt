package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class SetLoopingUseCase(private val soundScape: SoundScape) {

    /**
    Sets the looping of a single sound in a soundscape to be on or off.
     **/
    fun execute(index: Int, isLooping: Boolean): Completable {
        return soundScape.loopSingleSound(index, isLooping)
    }
}