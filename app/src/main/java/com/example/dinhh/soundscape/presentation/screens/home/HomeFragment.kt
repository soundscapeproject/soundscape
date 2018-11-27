package com.example.dinhh.soundscape.presentation.screens.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundFragment
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
            goToSoundsFragment("nature")
        }
        humanBtn.setOnClickListener {
            goToSoundsFragment("human")
        }
        machineBtn.setOnClickListener {
            goToSoundsFragment("machine")
        }
        storyBtn.setOnClickListener {
            goToSoundsFragment("story")
        }

    }

    private fun goToSoundsFragment(category: String){
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, SoundFragment.newInstance(category))
        fragmentTransaction?.addToBackStack(null)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
