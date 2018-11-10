package com.example.dinhh.soundscape.presentation.screens.record

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.presentation.dialog.SaveDialog
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity(), SaveDialog.SaveDialogListener {

    override fun onSaveDialogPositiveClick(recordName: String) {
        logD("SAVE DIALOG: ${recordName}")
    }

    override fun onSaveDialogNegativeClick(dialog: SaveDialog) {
        logD("CANCEL DIALOG")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        btnSave.setOnClickListener {

            val saveDialog = SaveDialog.newInstance("Save Record")

            saveDialog.show(supportFragmentManager, "SAVE_DIALOG")
        }

    }
}
