package com.example.dinhh.soundscape.device

import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.FileWriter

interface JsonWriter {

    fun writeJson(jsonString: String): Single<String>

    fun deleteJsonFile(): Completable
}

class JsonWriterImpl : JsonWriter {

    private val DEFAULT_FILE_NAME = "soundscapes.json"
    private var fileName: String = DEFAULT_FILE_NAME

    private val PATH = "/Soundscape/"
    private val FILEEXTENSION = ".json"

    private val containFolderPath = android.os.Environment.getExternalStorageDirectory().absolutePath + PATH

    override fun writeJson(jsonString: String): Single<String> {

        fileName = System.currentTimeMillis().toString() + FILEEXTENSION
        val output = containFolderPath + fileName

        val file = File(output)
        val writer = FileWriter(file)
        writer.write(jsonString)
        writer.flush()
        writer.close()

        return Single.just(output)
    }

    override fun deleteJsonFile(): Completable {

        return Completable.fromAction{
            val file = File(containFolderPath + fileName)

            if (file.exists()) {
                file.delete()
            }
        }
    }
}