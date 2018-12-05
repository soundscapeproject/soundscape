package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException


interface Player {

    fun playSound(selectedSound: String): Completable

    fun stopSound(): Completable

    fun onPlayComplete(): Completable

    fun loopSound()

    fun stopLoop()
}

class PlayerImpl: Player {

    private var mediaPlayer: MediaPlayer

    private var isLooping = false

    constructor() {
        mediaPlayer = MediaPlayer()
    }

    override fun playSound(soundUrl: String): Completable {
        return Completable.create {

            mediaPlayer = MediaPlayer()

            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(soundUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }.andThen(onPlayComplete())
    }

    override fun loopSound(){
        isLooping = true
    }

    override fun stopLoop(){
        isLooping = false
    }

    override fun onPlayComplete(): Completable {

        return Completable.create {complete ->
            mediaPlayer.setOnCompletionListener {
                if (!isLooping) {
                    complete.onComplete()
                }else{
                    mediaPlayer.seekTo(0)
                    mediaPlayer.start()
                }
            }
        }
    }

    override fun stopSound(): Completable {
        return Completable.create {
            if (mediaPlayer != null) {
                try {
                    if(mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    }
                    it.onComplete()
                } catch (e: IOException) {
                    it.onError(e)
                }
            }
        }
    }


}