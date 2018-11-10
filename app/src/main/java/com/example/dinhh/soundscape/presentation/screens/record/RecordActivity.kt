package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.presentation.dialog.SaveDialog
import kotlinx.android.synthetic.main.activity_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class RecordActivity : AppCompatActivity(), SaveDialog.SaveDialogListener {

    private val recordViewModel: RecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        recordViewModel.viewState.observe(this, Observer {
            it?.run(this@RecordActivity::handleView)
        })

        setupView()

        handleButtonClicked()

        btnSave.setOnClickListener {
            showSaveDialog()
        }
    }

    private fun setupView() {
        btnStopRecording.invisible()
        btnPlay.invisible()
        recordingTextView.invisible()
        btnSave.invisible()
    }

    private fun handleView(viewState: RecordViewState) = when (viewState) {
        is RecordViewState.Success -> {
            //Success State
        }

        is RecordViewState.Failure -> {
            Toast.makeText(this, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

        is RecordViewState.SaveRecordSuccess -> {
            logD("SAVE RECORD SUCCESS")
        }

        is RecordViewState.GetRecordsSuccess -> {
            logD("GET RECORDs SUCCESS: ${viewState.localRecords.toString()}")
        }
    }

    private fun handleButtonClicked() {
        btnStartRecording.setOnClickListener {
            btnStartRecording.invisible()
            btnStopRecording.visible()
            recordingTextView.visible()
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            recordViewModel.startRecording()
        }

        btnStopRecording.setOnClickListener {
            btnStartRecording.visible()
            btnStopRecording.invisible()
            recordingTextView.invisible()
            chronometer.stop()
            if (!btnPlay.isVisible()) {
                btnPlay.visible()
                btnSave.visible()
            }
            recordViewModel.stopRecording()
        }

        btnPlay.setOnClickListener {
            recordViewModel.playRecord()
        }

        btnSave.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }

        btnGetRecords.setOnClickListener {
            recordViewModel.getRecords()
        }
    }

    private fun showSaveDialog() {

        val saveDialog = SaveDialog.newInstance("Save Record")

        saveDialog.show(supportFragmentManager, "SAVE_DIALOG")
    }

    override fun onSaveDialogPositiveClick(recordName: String, category: String) {
        logD("SAVE DIALOG: ${recordName} : ${category}")
        recordViewModel.saveRecord()
    }

    override fun onSaveDialogNegativeClick(dialog: SaveDialog) {
        logD("CANCEL DIALOG")
    }
}
