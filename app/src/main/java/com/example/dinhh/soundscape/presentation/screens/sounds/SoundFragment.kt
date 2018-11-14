package com.example.dinhh.soundscape.presentation.screens.sounds


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dinhh.soundscape.R
import kotlinx.android.synthetic.main.fragment_sound.*
import kotlinx.android.synthetic.main.fragment_sound.view.*

private const val ARG_CATEGORY = "category"

class SoundFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_sound, container, false)

        view.txtCategory.text = category

        return view
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
