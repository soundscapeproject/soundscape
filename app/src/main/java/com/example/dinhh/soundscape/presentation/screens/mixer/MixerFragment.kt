package com.example.dinhh.soundscape.presentation.screens.mixer

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.presentation.screens.library.LibraryFragment
import kotlinx.android.synthetic.main.fragment_mixer.*
import java.util.ArrayList

private val testList = mutableListOf(
    MediaPlayer(),MediaPlayer(),MediaPlayer()
)

class MixerFragment : Fragment(){

    val mp1 = MediaPlayer()
    val mp2 = MediaPlayer()
    val mp3 = MediaPlayer()


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
        playAllBtn.setOnClickListener{
            for (i in 0 until testList.size) {
                testList[i].start()
            }
        }
    }

    private fun setupMixerView() {
        setTestMps()
        testList[0] = mp1
        testList[1] = mp2
        testList[2] = mp3
        mixerList.layoutManager = LinearLayoutManager(this.context!!)
        mixerList.adapter = MixerAdapter(testList)
    }

    fun setTestMps(){
        mp1.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/2/0/2_f732b2618af5a96/202_c024cc08c33e098.mp3?v=2015-11-17+21%3A58%3A52")
        mp2.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/6/2/9_053263decea4c51/629_731c23650979e7c.wav?v=2016-08-22+15%3A46%3A06")
        mp3.setDataSource("http://resourcespace.tekniikanmuseo.fi/filestore/2/0/9_f72baf3b8eaba1f/209_c3ad49f36964aab.mp3?v=2015-11-18+12%3A16%3A35")
        mp1.prepare()
        mp2.prepare()
        mp3.prepare()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MixerFragment()
    }
}