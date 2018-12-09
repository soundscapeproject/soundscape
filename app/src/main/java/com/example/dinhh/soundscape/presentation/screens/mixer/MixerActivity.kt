package com.example.dinhh.soundscape.presentation.screens.mixer

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.entity.SoundCategory
import com.example.dinhh.soundscape.data.entity.SoundScape
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.presentation.dialog.SaveSoundscapeDialog
import com.example.dinhh.soundscape.presentation.screens.sounds.MixerViewModel
import com.example.dinhh.soundscape.presentation.screens.sounds.MixerViewState
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundActivity
import kotlinx.android.synthetic.main.activity_mixer.*
import kotlinx.android.synthetic.main.topbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class MixerActivity : AppCompatActivity(),
    MixerAdapterViewHolderClicks, SaveSoundscapeDialog.SaveDialogListener {

    private lateinit var mixerAdapter: MixerAdapter
    private lateinit var saveSoundscapeDialog: SaveSoundscapeDialog

    private val mixerViewModel: MixerViewModel by viewModel()
    private var soundScapesList: MutableList<SoundscapeItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mixer)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mixerViewModel.getSoundScapes()

        mixerViewModel.viewState.observe(this, Observer {
            it?.run(this@MixerActivity::handleView)
        })

        setupMixerView()

        saveSoundscapeDialog = SaveSoundscapeDialog.newInstance(getString(R.string.title_save_soundscape_dialog))
        toolbar_title.text = "Workplace"


        //Play created soundscape
        playAllBtn.setOnClickListener {
            playAllBtn.invisible()
            stopAllBtn.visible()
            mixerViewModel.playSoundScapes()
            mixerAdapter.notifyDataSetChanged()
        }

        //Stop created soundscape
        stopAllBtn.setOnClickListener {
            playAllBtn.visible()
            stopAllBtn.invisible()
            mixerViewModel.stopSoundScapes()
            mixerAdapter.notifyDataSetChanged()
        }

        saveMixBtn.setOnClickListener {
            showSaveDialog()
        }

        clearBtn.setOnClickListener {
            mixerViewModel.clearSoundScapes()
            mixerViewModel.getSoundScapes()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mix_screen,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //SoundActivity.cameFromPopup = true
        when (item?.itemId){
            R.id.menuNature -> {
                getSoundsFromSelectedCategory(SoundCategory.NATURE.description)
            }
            R.id.menuHuman -> {
                getSoundsFromSelectedCategory(SoundCategory.HUMAN.description)
            }
            R.id.menuMachine -> {
                getSoundsFromSelectedCategory(SoundCategory.MACHINE.description)
            }
            R.id.menuRecord -> {
                getSoundsFromRecordCategory(item.title.toString())
            }
            R.id.menuStory -> {
                getSoundsFromSelectedCategory(SoundCategory.STORY.description)
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
        mixerViewModel.stopSoundScapes()
    }

    override fun onResume() {
        super.onResume()
        mixerViewModel.getSoundScapes()
    }

    private fun handleView(viewState: MixerViewState) {
        when (viewState) {

            MixerViewState.Loading -> {
//                progressBar.visible()
            }

            MixerViewState.Success -> {
//                progressBar.gone()
            }

            MixerViewState.PlaySoundScapeFinish -> {
                playAllBtn.visible()
                stopAllBtn.invisible()
            }

            is MixerViewState.PlaySoundFinish -> {
                toggleViewHolderIcon(viewState.index, false)
            }

            MixerViewState.RemoveSoundScapeSuccess -> {
                mixerViewModel.getSoundScapes()
            }

            is MixerViewState.Failure -> {
//                progressBar.gone()
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

            is MixerViewState.GetSoundScapesSuccess -> {
//                progressBar.gone()
                soundScapesList = viewState.soundScapeItems
                mixerAdapter.replaceData(viewState.soundScapeItems)
            }

            MixerViewState.SaveSoundScapeLoading -> {
                saveSoundscapeDialog.showLoading()
            }

            MixerViewState.SaveSoundScapeSuccess -> {
                saveSoundscapeDialog.hideLoading()
                dismissSaveDialog()
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
            }

            is MixerViewState.SaveSoundScapeFailure -> {
                saveSoundscapeDialog.hideLoading()
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSaveDialog() {
        saveSoundscapeDialog.show(supportFragmentManager, "SAVE_SOUNDSCAPE_DIALOG")
    }

    private fun dismissSaveDialog() {
        saveSoundscapeDialog.dismiss()
    }


    override fun onPlaySingleSoundScape(layoutPosition: Int) {
        mixerViewModel.playSingleSoundScape(layoutPosition)
        toggleViewHolderIcon(layoutPosition, true)
    }

    override fun onLoopSingleSound(layoutPosition: Int, isLooping: Boolean) {
        mixerViewModel.loopSingleSound(layoutPosition, isLooping)
    }


    override fun onStopSingleSoundScape(layoutPosition: Int) {
        mixerViewModel.stopSingleSoundScape(layoutPosition)
        toggleViewHolderIcon(layoutPosition, false)
    }

    override fun onRemoveSingleSoundScape(layoutPosition: Int) {
        mixerViewModel.removeSingleSoundScape(layoutPosition)
    }

    override fun onSaveDialogPositiveClick(soundScapeName: String) {
        val soundScapeList = soundScapesList.map { it -> SoundScape(
            it.title,
            it.length,
            it.category,
            it.source,
            it.volume
        )
        }
        val localSoundscape = LocalSoundscape(null, soundScapeName, soundScapeList)
        mixerViewModel.saveSoundScape(localSoundscape)
    }

    override fun onSaveDialogNegativeClick(recordDialog: SaveSoundscapeDialog) {
        // Do nothing
    }

    private fun getSoundsFromSelectedCategory(category: String){
        val intent = Intent(this, SoundActivity::class.java)
        intent.putExtra(SoundActivity.KEY_CATEGORY, category)
        intent.putExtra(SoundActivity.KEY_CAME_FROM_MIXER, true)
        startActivity(intent)
    }

    private fun getSoundsFromRecordCategory(category: String) {
        val intent = Intent(this, SoundActivity::class.java)
        intent.putExtra(SoundActivity.KEY_CAME_FROM_SAVED_SOUND, true)
        intent.putExtra(SoundActivity.KEY_CATEGORY, category)
        startActivity(intent)
    }

    private fun setupMixerView() {
        mixerList.layoutManager = LinearLayoutManager(this)
        mixerAdapter = MixerAdapter(soundScapesList, this)
        mixerList.adapter = mixerAdapter
    }

    private fun toggleViewHolderIcon(layoutPosition: Int, playing: Boolean) {
        val viewHolder = mixerList.findViewHolderForAdapterPosition(layoutPosition) as MixerAdapter.ViewHolder
        viewHolder.setPlayingState(playing)
    }
}
