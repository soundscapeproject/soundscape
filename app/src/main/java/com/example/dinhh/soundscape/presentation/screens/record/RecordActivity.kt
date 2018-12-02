package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.SoundType
import com.example.dinhh.soundscape.presentation.dialog.SaveDialog
import kotlinx.android.synthetic.main.activity_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class RecordActivity : AppCompatActivity(), SaveDialog.SaveDialogListener {

    private val recordViewModel: RecordViewModel by viewModel()

    private lateinit var saveDialog: SaveDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        recordViewModel.viewState.observe(this, Observer {
            it?.run(this@RecordActivity::handleView)
        })

        setupView()

        handleButtonClicked()
    }

    private fun setupView() {
        btnStopRecording.invisible()
        btnPlay.invisible()
        recordingTextView.invisible()
        btnSave.invisible()

        saveDialog = SaveDialog.newInstance(getString(R.string.title_save_record_dialog))
    }

    private fun handleView(viewState: RecordViewState) = when (viewState) {
        is RecordViewState.Success -> {}

        is RecordViewState.Failure -> {
            Toast.makeText(this, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

        is RecordViewState.SaveRecordLoading -> {
            saveDialog.showLoading()
        }

        is RecordViewState.SaveRecordSuccess -> {
            saveDialog.hideLoading()
            dismissSaveDialog()
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        }

        is RecordViewState.SaveRecordFailure -> {
            saveDialog.hideLoading()
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonClicked() {
        btnStartRecording.setOnClickListener {
            onStartBtnClicked()
        }

        btnStopRecording.setOnClickListener {
            onStopBtnclicked()
        }

        btnSave.setOnClickListener {
            showSaveDialog()
        }

        btnPlay.setOnClickListener {
            recordViewModel.playRecord()
        }
    }

    private fun onStartBtnClicked() {
        btnStartRecording.invisible()
        btnStopRecording.visible()
        recordingTextView.visible()
        chronometer.text = "00:00:00"
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        recordViewModel.startRecording()
    }

    private fun onStopBtnclicked() {
        btnStartRecording.visible()
        btnStopRecording.invisible()
        recordingTextView.invisible()
        chronometer.stop()
        recordViewModel.recordLength = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000
        if (!btnPlay.isVisible()) {
            btnPlay.visible()
            btnSave.visible()
        }
        recordViewModel.stopRecording()
    }

    private fun showSaveDialog() {
        saveDialog.show(supportFragmentManager, "SAVE_DIALOG")
    }

    private fun dismissSaveDialog() {
        saveDialog.dismiss()
    }

    override fun onSaveDialogPositiveClick(recordName: String, category: String) {

        val localRecord = LocalRecord(
            null,
            recordName,
            category,
            SoundType.SOUNDSCAPE.description,
            recordViewModel.fileUrl!!,
            recordViewModel.recordLength!!,
            false,
            false
        )
        recordViewModel.saveRecord(localRecord)
    }

    override fun onSaveDialogNegativeClick(dialog: SaveDialog) {
        logD("CANCEL DIALOG")
    }

    override fun onDestroy() {
        super.onDestroy()
        recordViewModel.deleteTempRecord()
    }
}
