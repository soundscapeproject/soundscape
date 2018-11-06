package com.example.dinhh.soundscape.presentation.screens.record

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.dinhh.soundscape.R
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File
import java.io.IOException

class RecordActivity : AppCompatActivity() {

    private val RECORD_AUDIO_REQUEST_CODE = 101
    private lateinit var fileName: String
    private lateinit var mPlayer: MediaPlayer
    private lateinit var myAudioRecorder: MediaRecorder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        btnStopRecording.invisible()
        btnPlay.invisible()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio()
        }

        btnStartRecording.setOnClickListener {
            btnStartRecording.invisible()
            btnStopRecording.visible()
            startRecording()
        }

        btnStopRecording.setOnClickListener {
            btnStartRecording.visible()
            btnStopRecording.invisible()
            if (!btnPlay.isVisible()){
                btnPlay.visible()
            }
            stopRecording()
        }

        btnPlay.setOnClickListener{
            playAudio()
        }
    }

    private fun startRecording(){
        myAudioRecorder = MediaRecorder()
        myAudioRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        val root = android.os.Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/Soundscape/Audios")
        if (!file.exists()) {
            file.mkdirs()
        }

        fileName = root.absolutePath + "/Soundscape/Audios/" + (System.currentTimeMillis().toString() + ".mp3")
        Log.d("filename", fileName)
        myAudioRecorder!!.setOutputFile(fileName)
        myAudioRecorder!!.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        try {
            myAudioRecorder!!.prepare()
            myAudioRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun stopRecording(){
        try {
            myAudioRecorder!!.stop()
            myAudioRecorder!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playAudio(){
        mPlayer = MediaPlayer()
        try {
            mPlayer!!.setDataSource(fileName)
            mPlayer!!.prepare()
            mPlayer!!.start()
        } catch (e: IOException) {
            Log.e("LOG_TAG", "prepare() failed")
        }
    }

    private fun getPermissionToRecordAudio() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_AUDIO_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
    }
}