package com.example.dinhh.soundscape.device

import io.reactivex.Completable
import io.reactivex.Single

interface SoundScape {

    fun addSound(soundScape: SoundscapeItem): Completable

    fun playSingleSoundScapes(index: Int): Single<Int>

    fun stopSingleSoundScapes(index: Int): Completable

    fun removeSingleSoundScapes(index: Int): Completable

    fun playSoundScapes(): Completable

    fun stopSoundScapes(): Completable

    fun clearSoundScapes(): Completable

    fun getSoundScapes(): Single<MutableList<SoundscapeItem>>

    fun loopSingleSound(index: Int, isLooping: Boolean)
}

data class SoundscapeItem(
    val title: String,
    val length: String,
    val category: String,
    var source: String,
    var volume: Int = 50,
    var isPlaying: Boolean = false,
    val player: Player = PlayerImpl()
)

class SoundScapeImpl: SoundScape{

    val soundsScapes: MutableList<SoundscapeItem> = mutableListOf()

    override fun clearSoundScapes(): Completable {
        return Completable.fromAction{
            soundsScapes.clear()
        }
    }

    override fun addSound(soundScape: SoundscapeItem): Completable {

        return Completable.fromAction{
            soundsScapes.add(soundScape)
        }
    }

    override fun removeSingleSoundScapes(index: Int): Completable {
        return stopSingleSoundScapes(index).andThen{
            soundsScapes.removeAt(index)
            it.onComplete()
        }
    }

    override fun playSingleSoundScapes(index: Int): Single<Int> {
        val soundScape = soundsScapes[index]
        soundScape.isPlaying = true
        return soundScape.player.playSound(soundScape.source).toSingleDefault(index)
    }

    override fun stopSingleSoundScapes(index: Int): Completable {
        val soundScape = soundsScapes[index]
        soundScape.isPlaying = false
        return soundScape.player.stopSound()
    }

    override fun loopSingleSound(index: Int, isLooping: Boolean) {
        val soundScape = soundsScapes[index]
        return soundScape.player.loopSound(isLooping)
    }

    override fun playSoundScapes(): Completable {

        val completableList = soundsScapes.map{
                soundScape ->
            soundScape.isPlaying = true
            soundScape.player.playSound(soundScape.source)
        }

        return Completable.mergeArray(*completableList.toTypedArray())
    }

    override fun stopSoundScapes(): Completable {

        val completableList = soundsScapes.map{
                soundScape ->
            soundScape.isPlaying = false
            soundScape.player.stopSound()
        }

        return Completable.mergeArray(*completableList.toTypedArray())
    }

    override fun getSoundScapes(): Single<MutableList<SoundscapeItem>> {
        return Single.just(this.soundsScapes)
    }
}