package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.presentation.adapter.SoundAdapter
import com.example.dinhh.soundscape.presentation.adapter.SoundAdapterViewHolderClicks
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import kotlinx.android.synthetic.main.activity_sound.*
import kotlinx.android.synthetic.main.topbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class SoundActivity : AppCompatActivity(),
    SoundAdapterViewHolderClicks {

    private val soundViewModel: SoundViewModel by viewModel()
    private lateinit var adapter: SoundAdapter
    private var isGoFromMixer: Boolean = false
    private var cameFromPopup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        soundViewModel.viewState.observe(this, Observer {
            it?.run(this@SoundActivity::handleView)
        })

        val category = intent.getStringExtra("category")
        cameFromPopup = intent.getBooleanExtra("cameFromPopup", false)
        isGoFromMixer = intent.getBooleanExtra("isGoFromMixer", false)

        soundViewModel.beginSearch(category!!)

        txt_Category_Name.text = category
        toolbar_title.text = "Category"

        setupListView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        cameFromPopup = false
        soundViewModel.stopSound()
    }

    override fun onPlayPauseToggle(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition][0]

        if (sound.isPlaying && soundViewModel.playingIndex == layoutPosition) {
            // RemoteSound is playing
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
            soundProgressBar.visible()
        }
        is SoundViewState.Success -> {
            soundProgressBar.gone()
            adapter.replaceData(viewState.listRemoteSound)
        }

        is SoundViewState.Failure -> {
            soundProgressBar.gone()
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
        val intent = Intent(this, MixerActivity::class.java)
        if(!cameFromPopup) {
            startActivity(intent)
        }
    }

    // Sets up the list of the sounds
    private fun setupListView() {
        adapter = SoundAdapter(ArrayList(), this)
        SoundAdapter.selectButtonIsVisible = isGoFromMixer
        soundList.layoutManager = LinearLayoutManager(this)
        soundList.adapter = adapter
    }
}
