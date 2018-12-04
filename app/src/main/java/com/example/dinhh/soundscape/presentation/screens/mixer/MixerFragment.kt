package com.example.dinhh.soundscape.presentation.screens.mixer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.entity.SoundCategory
import com.example.dinhh.soundscape.presentation.screens.sounds.MixerViewModel
import com.example.dinhh.soundscape.presentation.screens.sounds.MixerViewState
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundAdapter
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundFragment
import kotlinx.android.synthetic.main.fragment_mixer.*
import org.koin.android.viewmodel.ext.android.viewModel

class MixerFragment : Fragment(), MixerAdapterViewHolderClicks {

    private lateinit var mixerView: RecyclerView
    private lateinit var mixerAdapter: MixerAdapter

    private val mixerViewModel: MixerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mixerViewModel.getSoundScapes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflates the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mixer, container, false)

        mixerViewModel.viewState.observe(this, Observer {
            it?.run(this@MixerFragment::handleView)
        })

        mixerView = view.findViewById(R.id.mixerList) as RecyclerView
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMixerView()

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

        //Add sounds
        addNewMixerItem.setOnClickListener {
            showPopup()
        }
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
                throw(viewState.throwable)
                Toast.makeText(activity, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

            is MixerViewState.GetSoundScapesSuccess -> {
//                progressBar.gone()
                mixerAdapter.replaceData(viewState.soundScapeItems)
            }
        }
    }

    override fun onPlaySingleSoundScape(layoutPosition: Int) {
        mixerViewModel.playSingleSoundScape(layoutPosition)
        toggleViewHolderIcon(layoutPosition, true)
    }

    override fun onStopSingleSoundScape(layoutPosition: Int) {
        mixerViewModel.stopSingleSoundScape(layoutPosition)
        toggleViewHolderIcon(layoutPosition, false)
    }

    override fun onRemoveSingleSoundScape(layoutPosition: Int) {
        mixerViewModel.removeSingleSoundScape(layoutPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        Model.selectedSounds.clear()
    }

    private fun showPopup() {
        val popup: PopupMenu?
        popup = PopupMenu(activity, addNewMixerItem, Gravity.END)
        popup.inflate(R.menu.popup_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.naturePopupItem -> {
                    getSoundsFromSelectedCategory(SoundCategory.NATURE.description)
                }
                R.id.humanPopupItem -> {
                    getSoundsFromSelectedCategory(SoundCategory.HUMAN.description)
                }
                R.id.machinePopupItem -> {
                    getSoundsFromSelectedCategory(SoundCategory.MACHINE.description)
                }
                R.id.recordedPopupItem -> {
                    //MixerAdapter.itemBackground = ContextCompat.getDrawable(Activity(), R.drawable.category_background_story)!!
                }
            }
            true
        })
        popup.show()
    }

    private fun getSoundsFromSelectedCategory(category: String){
        SoundAdapter.selectButtonIsVisible = true
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, SoundFragment.newInstance(category))
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    private fun setupMixerView() {
        mixerList.layoutManager = LinearLayoutManager(this.context!!)
        mixerAdapter = MixerAdapter(Model.selectedSounds, this)
        mixerList.adapter = mixerAdapter
    }

    private fun toggleViewHolderIcon(layoutPosition: Int, playing: Boolean) {
        val viewHolder = mixerView.findViewHolderForAdapterPosition(layoutPosition) as MixerAdapter.ViewHolder
        viewHolder.setPlayingState(playing)
    }


    companion object {
        @JvmStatic
        fun newInstance() = MixerFragment()
    }
}