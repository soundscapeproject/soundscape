package com.example.dinhh.soundscape.presentation.screens.sounds

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.ListAdapter
import com.example.dinhh.soundscape.data.Model


class SoundsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Log.d("List: ", Model.sounds.toString())
        val view: View = inflater.inflate(R.layout.fragment_sounds, container, false)
        val listView = view.findViewById<ListView>(R.id.listView)

        listView.adapter = ListAdapter(context!!, Model.sounds)
        return view
    }
}