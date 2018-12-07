package com.example.dinhh.soundscape.presentation.screens.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.entity.SoundCategory
import com.example.dinhh.soundscape.presentation.screens.mixer.MixerActivity
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons(){

        natureBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.NATURE.description)
        }
        humanBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.HUMAN.description)
        }
        machineBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.MACHINE.description)
        }
        storyBtn.setOnClickListener {
            goToSoundsActivity(SoundCategory.STORY.description)
        }

        soundscapeCreatorButton.setOnClickListener {
            goToMixerActivity()
        }

    }

    private fun goToSoundsActivity(category: String) {
        val intent = Intent(activity, SoundActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    private fun goToMixerActivity(){
        val intent = Intent(activity, MixerActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}