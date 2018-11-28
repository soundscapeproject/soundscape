package com.example.dinhh.soundscape.presentation.screens.mixer

import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import kotlinx.android.synthetic.main.item_mixer.view.*

class MixerAdapter(private val items: MutableList<MediaPlayer>): RecyclerView.Adapter<ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_mixer, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.titleTextView.text = "Test sound " + position.toString()
        holder.itemView.itemSoundStopBtn.invisible()
        holder.itemView.itemSoundPlayBtn.setOnClickListener{
            items[position].start()
            holder.itemView.itemSoundStopBtn.visible()
        }
        holder.itemView.itemSoundStopBtn.setOnClickListener{
            items[position].stop()
            holder.itemView.itemSoundStopBtn.invisible()
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}