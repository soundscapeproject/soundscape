package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
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
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerFragment
import kotlinx.android.synthetic.main.fragment_sound.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_CATEGORY = "category"

class SoundFragment : Fragment(), SoundAdapterViewHolderClicks, SoundSelected {

    private val soundViewModel: SoundViewModel by viewModel()
    private lateinit var adapter: SoundAdapter
    private var category: String? = null
    private lateinit var soundList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
        soundViewModel.viewState.observe(this, Observer {
            it?.run(this@SoundFragment::handleView)
        })
        soundViewModel.beginSearch(category!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflates the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sound, container, false)
        soundList = view.findViewById(R.id.soundList) as RecyclerView

        setupListView()

        return view
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
        selectSound(layoutPosition)
    }


    private fun selectSound(layoutPosition: Int){
        val sound = adapter.getData()[layoutPosition][0]
        soundViewModel.addSelectedSound(sound.downloadLink, layoutPosition, sound.title, sound.length.toInt(), sound.category, 50)
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

        // View state behaviors for playing the selected sound
        SoundViewState.PlayFinish -> {
            toggleViewHolderIcon(soundViewModel.playingIndex, false)
            soundViewModel.playingIndex = -1
        }

        // View state behaviors for adding the selected sound to the soundscape
        SoundViewState.AddSelectedLoading -> {
            progressBar.visible()
        }
        is SoundViewState.AddSelectedSoundSuccess ->{
            goToMixer()
        }

        // View state behaviors in case of failure
        is SoundViewState.Failure -> {
            progressBar.gone()
            Toast.makeText(activity, "Error: ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
        else -> {
        }
    }

    private fun goToMixer() {
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, MixerFragment())
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    // Sets up the list of the sounds
    private fun setupListView() {
        adapter = SoundAdapter(ArrayList(), this,this)
        soundList.layoutManager = LinearLayoutManager(this.context!!)
        soundList.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            SoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}