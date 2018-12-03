package com.example.dinhh.soundscape.device

import android.media.MediaRecorder
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.IOException

interface Record {
    fun startRecording(): Completable

    fun stopRecording(): Single<String>

    fun deleteTempFile(): Completable

    fun resetTempFile(): Completable
}

class RecordImpl: Record {

    private lateinit var myAudioRecorder: MediaRecorder
    private val DEFAULT_FILE_NAME = "untitled-recording.mp3"
    private var fileName: String = DEFAULT_FILE_NAME

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

    override fun resetTempFile(): Completable {
        return Completable.fromAction {
            fileName = DEFAULT_FILE_NAME
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
