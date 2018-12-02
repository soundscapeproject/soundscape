package com.example.dinhh.soundscape.presentation.screens.savedrecord

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.presentation.screens.library.SavedRecordAdapter
import kotlinx.android.synthetic.main.activity_saved_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class SavedRecord : AppCompatActivity() {

    private val savedRecordViewModel: SavedRecordViewModel by viewModel()

    private lateinit var adapter: SavedRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_record)

        savedRecordViewModel.viewState.observe(this, Observer {
            it?.run(this@SavedRecord::handleView)
        })

        savedRecordViewModel.getRecords()

        setupViews()
    }

    private fun handleView(viewState: SavedSoundViewState) {
        when (viewState) {
            SavedSoundViewState.Loading -> {
                progressBar.visible()
            }

            is SavedSoundViewState.GetRecordsSuccess -> {
                handleGetSavedSoundSucessView(viewState.localRecords as MutableList<LocalRecord>)
            }

            is SavedSoundViewState.Failure -> {
                progressBar.gone()
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleGetSavedSoundSucessView(localRecords: MutableList<LocalRecord>) {
        progressBar.gone()

        if (localRecords.isEmpty()) {
            rvSavedRecord.gone()
            txtNoRecord.visible()
        } else {
            rvSavedRecord.visible()
            txtNoRecord.gone()

            adapter.replaceData(localRecords)
        }
    }

    private fun setupViews() {
        adapter = SavedRecordAdapter(ArrayList())
        val layoutManager = LinearLayoutManager(this)
        rvSavedRecord.layoutManager = layoutManager
        rvSavedRecord.adapter = adapter
    }
}
