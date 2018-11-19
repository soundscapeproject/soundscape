package com.example.dinhh.soundscape.presentation.screens.sounds

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.entity.Sound
import kotlinx.android.synthetic.main.item_sound.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class SoundAdapter(val items: List<List<Sound>>, private val soundViewModel: SoundViewModel): RecyclerView.Adapter<ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sound, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[position][0]
        holder.itemView.titleTextView.text = list.title
        holder.itemView.lengthTextView.text = list.length + " sec"

        holder.itemView.itemSoundPlayBtn.setOnClickListener{
            Log.d("Clicked position :", position.toString())
            soundViewModel.playSound(list.downloadLink)

        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}