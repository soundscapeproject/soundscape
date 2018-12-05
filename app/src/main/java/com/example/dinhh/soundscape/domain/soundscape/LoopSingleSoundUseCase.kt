package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.device.SoundScape

class LoopSingleSoundUseCase(private val soundScape: SoundScape) {

    fun execute(index: Int) {
        return soundScape.loopSingleSound(index)
    }
}