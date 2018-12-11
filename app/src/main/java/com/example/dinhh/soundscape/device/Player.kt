package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.IOException


interface Player {

    fun playSound(soundUrl: String): Completable

    fun stopSound(): Completable

    fun loopSound(looping: Boolean)

    fun releaseSound(): Completable

    fun changeVolume(progress: Int): Completable
}

class PlayerImpl : Player {

    private var mMediaPlayer: MediaPlayer? = null
    private var isLooping: Boolean = false

    private fun initializeMediaPlayer(): Completable {
        return Completable.fromAction {
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer()
            }
        }
    }

    private fun loadMedia(source: String): Completable {

        return initializeMediaPlayer().andThen {

            try {
                mMediaPlayer?.setDataSource(source)
                mMediaPlayer?.prepare()

                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    private fun release(): Completable {

        return Completable.create {
            if (mMediaPlayer != null) {
                mMediaPlayer?.release()
                mMediaPlayer = null
                it.onComplete()
            }
        }
    }

    private fun isPlaying(): Boolean {

        if (mMediaPlayer != null) {
            return mMediaPlayer?.isPlaying() ?: false
        }

        return false
    }

    private fun seekTo(position: Int): Completable {
        return Completable.create {
            if (mMediaPlayer != null) {
                mMediaPlayer?.seekTo(position)
                it.onComplete()
            }
        }
    }

    override fun changeVolume(progress: Int): Completable {

        return Completable.fromAction{
            val volumne = progress / 100f
            mMediaPlayer?.setVolume(volumne,volumne)
        }
    }

    private fun play(): Completable {
        return Completable.create { complete ->
            if (mMediaPlayer != null && !isPlaying()) {
                mMediaPlayer?.start()


                mMediaPlayer?.setOnCompletionListener {
                    if (!isLooping) {
                        complete.onComplete()
                    } else {
                        mMediaPlayer?.seekTo(0)
                        mMediaPlayer?.start()
                    }
                }
            }
        }
    }

    private fun reset(source: String): Completable {

        return Completable.fromAction {
            mMediaPlayer?.reset()
        }.andThen(loadMedia(source))
    }

    private fun pause(): Completable {

        return Completable.create {
            if (mMediaPlayer != null && isPlaying()) {
                mMediaPlayer?.pause()
            }

            it.onComplete()
        }

    }

    override fun playSound(soundUrl: String): Completable {

        return reset(soundUrl).andThen(play())
    }

    override fun stopSound(): Completable {

        return pause()
    }

    override fun releaseSound(): Completable {

        return release()
    }


    override fun loopSound(looping: Boolean) {
        isLooping = looping
    }
}