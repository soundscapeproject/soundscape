package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import com.example.dinhh.soundscape.common.logD
import io.reactivex.Completable
import io.reactivex.Single
import java.io.IOException

interface SoundScape {

    fun addSound(soundScape: SoundscapeItem): Completable

    fun playSingleSoundScapes(index: Int): Completable

    fun stopSingleSoundScapes(index: Int): Completable

    fun removeSingleSoundScapes(index: Int): Completable

    fun playSoundScapes(): Completable

    fun stopSoundScapes(): Completable

    fun clearSoundScapes(): Completable

    fun getSoundScapes(): Single<MutableList<SoundscapeItem>>
}

data class SoundscapeItem(
    val title: String,
    val length: Int,
    val category: String,
    var source: String,
    var volume: Int = 50,
    val player: MediaPlayer = MediaPlayer()
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

    override fun playSingleSoundScapes(index: Int): Completable {
        return Completable.create{
            val soundScape = soundsScapes[index]
            val mediaPlayer = soundScape.player

            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(soundScape.source)
                mediaPlayer.prepare()
                mediaPlayer.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun stopSingleSoundScapes(index: Int): Completable {
        return Completable.create {
            val soundScape = soundsScapes[index]
            val mediaPlayer = soundScape.player

            try {
                mediaPlayer.stop()
                mediaPlayer.release()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun playSoundScapes(): Completable {
        return Completable.fromAction{
            soundsScapes.forEach { (soundsScape, index) ->
                playSingleSoundScapes(index)
            }
        }
    }

    override fun stopSoundScapes(): Completable {
        return Completable.fromAction{
            soundsScapes.forEach { (soundsScape, index) -> stopSingleSoundScapes(index)}
        }
    }

    override fun getSoundScapes(): Single<MutableList<SoundscapeItem>> {
        return Single.just(this.soundsScapes)
    }
}