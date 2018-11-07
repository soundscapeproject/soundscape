package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import io.reactivex.Completable
import java.io.File
import java.io.IOException

interface Record {
    fun startRecording(): Completable

    fun stopRecording(): Completable

    fun playAudio(): Completable
}

class RecordImpl: Record {

    private var mPlayer: MediaPlayer
    private var myAudioRecorder: MediaRecorder
    private var fileName: String? = null

    private val PATH = "/Soundscape/Audios"
    private val AUDIOEXTENSION = ".mp3"

    constructor() {
        mPlayer = MediaPlayer()
        myAudioRecorder = MediaRecorder()
    }

    override fun startRecording(): Completable {
        return Completable.create {

            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

            val root = android.os.Environment.getExternalStorageDirectory()
            val file = File(root.absolutePath + PATH)
            if (!file.exists()) {
                file.mkdirs()
            }

            fileName = root.absolutePath + PATH + (System.currentTimeMillis().toString() + AUDIOEXTENSION)
            myAudioRecorder.setOutputFile(fileName)
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)

            try {
                myAudioRecorder.prepare()
                myAudioRecorder.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun stopRecording(): Completable {
        return Completable.create {
            try {
                myAudioRecorder.stop()
                myAudioRecorder.release()
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun playAudio(): Completable {
        return Completable.create {
            try {
                mPlayer.setDataSource(fileName)
                mPlayer.prepare()
                mPlayer.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }
}
