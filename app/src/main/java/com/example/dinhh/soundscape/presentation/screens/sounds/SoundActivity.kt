package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.presentation.base.RecyclerViewListener
import com.example.dinhh.soundscape.presentation.helper.SwipeToDeleteCallback
import com.example.dinhh.soundscape.presentation.screens.entity.DisplaySound
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import com.example.dinhh.soundscape.presentation.screens.record.RecordActivity
import kotlinx.android.synthetic.main.activity_sound.*
import kotlinx.android.synthetic.main.topbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class SoundActivity : AppCompatActivity(),
    SoundAdapterViewHolderClicks {

    private val soundViewModel: SoundViewModel by viewModel()
    private lateinit var adapter: SoundAdapter
    private var cameFromMixer: Boolean = false
    private var cameFromSavedSound: Boolean = false

    companion object {
        val KEY_CAME_FROM_SAVED_SOUND = "cameFromSavedSound"
        val KEY_CAME_FROM_MIXER = "cameFromMixer"
        val KEY_CATEGORY = "category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        soundViewModel.viewState.observe(this, Observer {
            it?.run(this@SoundActivity::handleView)
        })

        val category = intent.getStringExtra(KEY_CATEGORY)
        cameFromMixer = intent.getBooleanExtra(KEY_CAME_FROM_MIXER, false)
        cameFromSavedSound = intent.getBooleanExtra(KEY_CAME_FROM_SAVED_SOUND, false)

        if (cameFromSavedSound) {
            soundViewModel.getRecords()
        } else {
            soundViewModel.beginSearch(category!!)
        }

        toolbar_title.text = category

        setupListView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if (cameFromSavedSound) {
            menuInflater.inflate(R.menu.menu_record, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuRecord -> {
                goToRecordActivity()
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        cameFromMixer = false
        soundViewModel.stopSound()
    }

    override fun onResume() {
        super.onResume()

        if (cameFromSavedSound) {
            soundViewModel.getRecords()
        }
    }

    override fun onPlayPauseToggle(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]

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

    override fun uploadSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]

        soundViewModel.uploadSound(LocalRecord.displaySoundToLocalRecord(sound))
    }

    private fun goToRecordActivity() {
        startActivity(Intent(this, RecordActivity::class.java))
    }

    private fun playSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        sound.isPlaying = true
        soundViewModel.playingIndex = layoutPosition

        soundViewModel.playSound(sound.downloadLink)

        toggleViewHolderIcon(layoutPosition, true)
    }

    private fun stopSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        sound.isPlaying = false
        soundViewModel.playingIndex = -1

        soundViewModel.stopSound()

        toggleViewHolderIcon(layoutPosition, false)
    }

    private fun remoteSound(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        soundViewModel.deleteRecord(sound.id)
        adapter.removeAt(layoutPosition)
    }

    private fun addSoundToSoundscape(layoutPosition: Int) {
        val sound = adapter.getData()[layoutPosition]
        val soundscapeItem =
            SoundscapeItem(sound.title, sound.length ?: "" , sound.category, sound.downloadLink)
        soundViewModel.addSoundScape(soundscapeItem)
        goToMixer()
    }

    private fun toggleViewHolderIcon(layoutPosition: Int, playing: Boolean) {
        val viewHolder = soundList.findViewHolderForAdapterPosition(layoutPosition) as SoundAdapter.ViewHolder
        viewHolder.setPlayingState(playing)
    }

    private fun changeTitleBar(soundList: List<DisplaySound>) {

        val numberOfSounds = soundList.count()

        when(numberOfSounds) {
            1 -> {
                txt_Category_Name.text = "${numberOfSounds} Sound"
            }

            else -> {
                txt_Category_Name.text = "${numberOfSounds} Sounds"
            }
        }
    }

    private fun handleView(viewState: SoundViewState) = when (viewState) {

        // View state behaviors for loading the list of sounds
        SoundViewState.Loading -> {
            soundProgressBar.visible()
        }
        is SoundViewState.GetRemoteSoundSuccess -> {
            soundProgressBar.gone()

            val listRemoteSound = viewState.listRemoteSound
            val displaySoundList = listRemoteSound.map { element ->
                DisplaySound.remoteSoundToDisplaySound(element[0])
            }

            changeTitleBar(displaySoundList)
            adapter.replaceData(displaySoundList)
        }

        is SoundViewState.GetRecordsSuccess -> {
            soundProgressBar.gone()

            val displaySoundList = viewState.localRecords.map {element ->
                DisplaySound.localRecordToDisplaySound(element)
            }

            changeTitleBar(displaySoundList)
            adapter.replaceData(displaySoundList)
        }

        is SoundViewState.Failure -> {
            soundProgressBar.gone()
            Toast.makeText(this, "Error: ${viewState.throwable.message}", Toast.LENGTH_SHORT).show()
        }

        is SoundViewState.Success -> {
            soundProgressBar.gone()
        }

        SoundViewState.UploadSuccess -> {
            soundViewModel.getRecords()
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
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
        if(!cameFromMixer) {
            startActivity(intent)
        }
    }

    // Sets up the list of the sounds
    private fun setupListView() {
        adapter = SoundAdapter(ArrayList(), this)

        adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                addSoundToSoundscape(position)
            }
        })

        soundList.layoutManager = LinearLayoutManager(this)
        soundList.adapter = adapter

        if (cameFromSavedSound) {
            val swipeHandler = object : SwipeToDeleteCallback(this) {

                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                    val position = p0.layoutPosition
                    remoteSound(position)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(soundList)
        }
    }
}
