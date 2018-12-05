package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.device.SoundScape

class StopLoopSingleSoundUseCase(private val soundScape: SoundScape) {

    fun execute(index: Int) {
        return soundScape.stopLoopSingleSound(index)
    }
}