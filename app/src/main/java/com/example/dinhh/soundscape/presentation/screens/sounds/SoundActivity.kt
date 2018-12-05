package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import kotlinx.android.synthetic.main.fragment_sound.*
import org.koin.android.viewmodel.ext.android.viewModel

class SoundActivity : AppCompatActivity(), SoundAdapterViewHolderClicks {

    private val soundViewModel: SoundViewModel by viewModel()
    private lateinit var adapter: SoundAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        soundViewModel.viewState.observe(this, Observer {
            it?.run(this@SoundActivity::handleView)
        })

        val category = intent.getStringExtra("category")

        soundViewModel.beginSearch(category!!)

        txt_Category_Name.text = category

        setupListView()
    }

    override fun onPause() {
        super.onPause()
        soundViewModel.stopSound()
    }

    override fun onPlayPauseToggle(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition][0]

        if (sound.isPlaying && soundViewModel.playingIndex == layoutPosition) {
            // Sound is playing
            stopSound(layoutPosition)
        } else if (soundViewModel.playingIndex == -1) {
            // Nothing is playing
            playSound(layoutPosition)
        } else if (soundViewModel.playingIndex != layoutPosition) {
            // other sound is playing
            stopSound(soundViewModel.playingIndex)
            playSound(layoutPosition)
        }
    }

    private fun playSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition][0]
        sound.isPlaying = true
        soundViewModel.playingIndex = layoutPosition

        soundViewModel.playSound(sound.downloadLink)

        toggleViewHolderIcon(layoutPosition, true)
    }

    private fun stopSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition][0]
        sound.isPlaying = false
        soundViewModel.playingIndex = -1

        soundViewModel.stopSound()

        toggleViewHolderIcon(layoutPosition, false)
    }

    override fun addSoundToSoundscape(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition][0]
        val soundscapeItem =
            SoundscapeItem(sound.title, sound.length.toInt(), sound.category, sound.downloadLink)
        soundViewModel.addSoundScape(soundscapeItem)
        goToMixer()
    }

    private fun toggleViewHolderIcon(layoutPosition: Int, playing: Boolean) {
        val viewHolder = soundList.findViewHolderForAdapterPosition(layoutPosition) as SoundAdapter.ViewHolder
        viewHolder.setPlayingState(playing)
    }

    private fun handleView(viewState: SoundViewState) = when (viewState) {

        // View state behaviors for loading the list of sounds
        SoundViewState.Loading -> {
            progressBar.visible()
        }
        is SoundViewState.Success -> {
            progressBar.gone()
            adapter.replaceData(viewState.listSound)
        }

        is SoundViewState.Failure -> {
            Toast.makeText(this, "Error: ${viewState.throwable.message}", Toast.LENGTH_SHORT).show()
        }

        // View state behaviors for playing the selected sound
        SoundViewState.PlayFinish -> {
            toggleViewHolderIcon(soundViewModel.playingIndex, false)
            soundViewModel.playingIndex = -1
        }
    }

    private fun goToMixer() {
        finish()
    }

    // Sets up the list of the sounds
    private fun setupListView() {
        adapter = SoundAdapter(ArrayList(), this)
        soundList.layoutManager = LinearLayoutManager(this)
        soundList.adapter = adapter
    }
}
