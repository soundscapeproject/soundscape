package com.example.dinhh.soundscape.presentation.screens.mixer

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageButton
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.R.id.addNewMixerItem
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.presentation.screens.record.RecordActivity
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundAdapter
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundFragment
import kotlinx.android.synthetic.main.fragment_mixer.*

class MixerFragment : Fragment(){

    private lateinit var mixerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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

        val btnMic = view.findViewById<ImageButton>(R.id.micBtn)
        btnMic.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.mix_screen,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.item1 -> {
                getSoundsFromSelectedCategory(item.title.toString())
            }
            R.id.item2 -> {
                getSoundsFromSelectedCategory(item.title.toString())
            }
            R.id.item3 -> {
                getSoundsFromSelectedCategory(item.title.toString())
            }
            R.id.item4 -> {
                getSoundsFromSelectedCategory(item.title.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        Model.selectedSounds.clear()
    }


    /*private fun showPopup() {
        val popup: PopupMenu?
        popup = PopupMenu(activity!!.applicationContext, addNewMixerItem, Gravity.END)
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
                    //MixerAdapter.itemBackground = ContextCompat.getDrawable(Activity(), R.drawable.category_background_story)!!
                }
            }
            true
        })
        popup.show()
    }*/

    private fun getSoundsFromSelectedCategory(category: String){
        SoundFragment.categoryName = category
        SoundAdapter.selectButtonIsVisible = true
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, SoundFragment.newInstance(category))
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    private fun setupMixerView() {
        mixerList.layoutManager = LinearLayoutManager(this.context!!)
        mixerList.adapter = MixerAdapter(Model.selectedSounds)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MixerFragment()
    }
}