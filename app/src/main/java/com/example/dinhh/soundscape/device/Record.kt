package com.example.dinhh.soundscape.device

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import com.example.dinhh.soundscape.common.logD
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.IOException

interface Record {
    fun startRecording(): Completable

    fun stopRecording(): Single<String>

    fun playAudio(): Completable

    fun deleteTempFile(): Completable
}

class RecordImpl: Record {

    private lateinit var mPlayer: MediaPlayer
    private lateinit var myAudioRecorder: MediaRecorder
    private var fileName: String = "untitled-recording.mp3"

    private val PATH = "/Soundscape/"
    private val AUDIOEXTENSION = ".mp3"

    private val containFolderPath = android.os.Environment.getExternalStorageDirectory().absolutePath + PATH

    override fun startRecording(): Completable {

        return  deleteTempFile().concatWith(  Completable.create {

            myAudioRecorder = MediaRecorder()
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

            val containFolderFile = File(containFolderPath)
            if (!containFolderFile.exists()) {
                containFolderFile.mkdirs()
            }

            fileName = System.currentTimeMillis().toString() + AUDIOEXTENSION

            val output = containFolderPath + fileName

            myAudioRecorder.setOutputFile(output)
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)

            try {
                myAudioRecorder.prepare()
                myAudioRecorder.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        })
    }

    override fun stopRecording(): Single<String> {

        return Single.create {
            try {
                myAudioRecorder.stop()
                myAudioRecorder.release()
                it.onSuccess(containFolderPath + fileName)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    override fun playAudio(): Completable {
        return Completable.create {
            try {
                mPlayer = MediaPlayer()
                mPlayer.setDataSource(containFolderPath + fileName)
                mPlayer.prepare()
                mPlayer.start()
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    override fun deleteTempFile(): Completable {

        return Completable.fromAction {
            val file = File(containFolderPath + fileName)

            if(file.exists()) {
                file.delete()
            }
        }
    }
}
