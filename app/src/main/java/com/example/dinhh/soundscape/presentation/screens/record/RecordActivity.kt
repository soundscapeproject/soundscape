package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.isVisible
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.SoundType
import com.example.dinhh.soundscape.presentation.dialog.SaveRecordDialog
import kotlinx.android.synthetic.main.activity_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class RecordActivity : AppCompatActivity(), SaveRecordDialog.SaveDialogListener {

    private val recordViewModel: RecordViewModel by viewModel()

    private lateinit var saveRecordDialog: SaveRecordDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recordViewModel.viewState.observe(this, Observer {
            it?.run(this@RecordActivity::handleView)
        })

        setupView()

        handleButtonClicked()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.profile_screen, menu)
        return true

    }

    private fun setupView() {
        btnStopRecording.invisible()
        btnPlay.invisible()
        recordingTextView.invisible()
        btnSave.invisible()

        saveRecordDialog = SaveRecordDialog.newInstance(getString(R.string.title_save_record_dialog))
    }

    private fun handleView(viewState: RecordViewState) = when (viewState) {
        is RecordViewState.Success -> {}

        is RecordViewState.Failure -> {
            Toast.makeText(this, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

        is RecordViewState.SaveRecordLoading -> {
            saveRecordDialog.showLoading()
        }

        is RecordViewState.SaveRecordSuccess -> {
            saveRecordDialog.hideLoading()
            dismissSaveDialog()
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        }

        is RecordViewState.SaveRecordFailure -> {
            saveRecordDialog.hideLoading()
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
        saveRecordDialog.show(supportFragmentManager, "SAVE_DIALOG")
    }

    private fun dismissSaveDialog() {
        saveRecordDialog.dismiss()
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

    override fun onSaveDialogNegativeClick(recordDialog: SaveRecordDialog) {
        logD("CANCEL DIALOG")
    }

    override fun onDestroy() {
        super.onDestroy()
        recordViewModel.deleteTempRecord()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
