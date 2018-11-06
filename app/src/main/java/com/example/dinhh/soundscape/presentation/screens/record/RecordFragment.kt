package com.example.dinhh.soundscape.presentation.screens.record


import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.File
import java.io.IOException

class RecordFragment : Fragment() {

    private lateinit var fileName: String
    private lateinit var mPlayer: MediaPlayer
    private lateinit var myAudioRecorder: MediaRecorder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleButtonClicked()
    }

    private fun setupView() {
        btnStopRecording.invisible()
        btnPlay.invisible()
    }

    private fun handleButtonClicked() {
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
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        val root = android.os.Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/Soundscape/Audios")
        if (!file.exists()) {
            file.mkdirs()
        }

        fileName = root.absolutePath + "/Soundscape/Audios/" + (System.currentTimeMillis().toString() + ".mp3")
        Log.d("filename", fileName)
        myAudioRecorder.setOutputFile(fileName)
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        try {
            myAudioRecorder.prepare()
            myAudioRecorder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun stopRecording(){
        try {
            myAudioRecorder.stop()
            myAudioRecorder.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playAudio(){
        mPlayer = MediaPlayer()
        try {
            mPlayer.setDataSource(fileName)
            mPlayer.prepare()
            mPlayer.start()
        } catch (e: IOException) {
            Log.e("LOG_TAG", "prepare() failed")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecordFragment()
    }
}
