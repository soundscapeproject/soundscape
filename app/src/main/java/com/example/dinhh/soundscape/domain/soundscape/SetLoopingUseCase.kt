package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.device.SoundScape

class SetLoopingUseCase(private val soundScape: SoundScape) {

    fun execute(index: Int, isLooping: Boolean) {
        return soundScape.loopSingleSound(index, isLooping)
    }
}