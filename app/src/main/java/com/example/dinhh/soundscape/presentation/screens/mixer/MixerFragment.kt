package com.example.dinhh.soundscape.presentation.screens.mixer

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundAdapter
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundFragment
import kotlinx.android.synthetic.main.fragment_mixer.*
import kotlinx.android.synthetic.main.item_sound.*

class MixerFragment : Fragment(){

    private lateinit var mixerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflates the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mixer, container, false)
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
            for (i in 0 until Model.selectedSounds.size) {
                Model.selectedSounds[i].sound.start()
            }
        }

        //Stop created soundscape
        stopAllBtn.setOnClickListener {
            playAllBtn.visible()
            stopAllBtn.invisible()
                for (i in 0 until Model.selectedSounds.size) {
                    Model.selectedSounds[i].sound.pause()
                    Model.selectedSounds[i].sound.seekTo(0)
                }
        }

        //Add sounds
        addSoundBtn.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        val popup: PopupMenu?
        popup = PopupMenu(activity!!.applicationContext, addSoundBtn, Gravity.END)
        popup.inflate(R.menu.popup_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.naturePopupItem -> {
                    getSoundsFromSelectedCategory(item.title.toString())
                }
                R.id.humanPopupItem -> {
                    getSoundsFromSelectedCategory(item.title.toString())
                }
                R.id.machinePopupItem -> {
                    getSoundsFromSelectedCategory(item.title.toString())
                }
                R.id.recordedPopupItem -> {
                    //getSoundsFromSelectedCategory(item.title.toString())
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
        /*setTestMps()
        Model.selectedSounds.add(mp1)
        Model.selectedSounds.add(mp2)
        Model.selectedSounds.add(mp3)*/

        mixerList.layoutManager = LinearLayoutManager(this.context!!)
        mixerList.adapter = MixerAdapter(Model.selectedSounds)
    }
/*
    fun setTestMps(){
        mp1.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/2/0/2_f732b2618af5a96/202_c024cc08c33e098.mp3?v=2015-11-17+21%3A58%3A52")
        mp2.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/6/2/9_053263decea4c51/629_731c23650979e7c.wav?v=2016-08-22+15%3A46%3A06")
        mp3.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/2/0/9_f72baf3b8eaba1f/209_c3ad49f36964aab.mp3?v=2015-11-18+12%3A16%3A35")
        mp1.prepare()
        mp2.prepare()
        mp3.prepare()
    }*/

    companion object {
        @JvmStatic
        fun newInstance() = MixerFragment()
    }
}