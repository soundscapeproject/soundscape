package com.example.dinhh.soundscape.presentation.screens.savedrecord

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.presentation.screens.library.SavedRecordAdapter
import com.example.dinhh.soundscape.presentation.screens.library.SavedRecordAdapterViewHolderClicks
import kotlinx.android.synthetic.main.fragment_saved_record.*
import org.koin.android.viewmodel.ext.android.viewModel

class SavedRecordFragment : Fragment(), SavedRecordAdapterViewHolderClicks {

    private val savedRecordViewModel: SavedRecordViewModel by viewModel()
    private lateinit var adapter: SavedRecordAdapter

    private lateinit var rvSavedRecord: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_saved_record, container, false)

        savedRecordViewModel.viewState.observe(this, Observer {
            it?.run(this@SavedRecordFragment::handleView)
        })
        rvSavedRecord = view.findViewById(R.id.rvSavedRecord) as RecyclerView

        savedRecordViewModel.getRecords()

        setupViews()

        return view
    }

    private fun handleView(viewState: SavedSoundViewState) {
        when (viewState) {

            // View state behaviors for getting sounds
            SavedSoundViewState.Loading -> {
                progressBar.visible()
            }

            is SavedSoundViewState.GetRecordsSuccess -> {
                handleGetSavedSoundSucessView(viewState.localRecords as MutableList<LocalRecord>)
            }

            is SavedSoundViewState.Failure -> {
                progressBar.gone()
                Toast.makeText(activity, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

            // View state behaviors for playing the selected sound
            SavedSoundViewState.PlayFinish -> {
                toggleViewHolderIcon(savedRecordViewModel.playingIndex, false)
                savedRecordViewModel.playingIndex = -1
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

            val savedRecords = localRecords.map { localRecord ->  SavedRecord(
                localRecord.soundId!!,
                localRecord.title,
                localRecord.url,
                localRecord.length_sec,
                localRecord.isUploaded,
                false
            )} as MutableList<SavedRecord>

            adapter.replaceData(savedRecords)
        }
    }

    override fun onPlayPauseToggle(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]

        if (sound.isPlaying && savedRecordViewModel.playingIndex == layoutPosition) {
            // Sound is playing
            stopSound(layoutPosition)
        } else if (savedRecordViewModel.playingIndex == -1) {
            // Nothing is playing
            playSound(layoutPosition)
        } else if (savedRecordViewModel.playingIndex != layoutPosition) {
            // other sound is playing
            stopSound(savedRecordViewModel.playingIndex)
            playSound(layoutPosition)
        }
    }

    private fun playSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        sound.isPlaying = true
        savedRecordViewModel.playingIndex = layoutPosition

        savedRecordViewModel.playSound(sound.url)

        toggleViewHolderIcon(layoutPosition, true)
    }

    private fun stopSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        sound.isPlaying = false
        savedRecordViewModel.playingIndex = -1

        savedRecordViewModel.stopSound()

        toggleViewHolderIcon(layoutPosition, false)
    }

    private fun toggleViewHolderIcon(layoutPosition: Int, playing: Boolean) {
        val viewHolder = rvSavedRecord.findViewHolderForAdapterPosition(layoutPosition) as SavedRecordAdapter.ViewHolder
        viewHolder.setPlayingState(playing)
    }

    private fun setupViews() {
        adapter = SavedRecordAdapter(ArrayList(), this)
        val layoutManager = LinearLayoutManager(activity)
        rvSavedRecord.layoutManager = layoutManager
        rvSavedRecord.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavedRecordFragment()
    }
}
