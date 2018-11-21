package com.example.dinhh.soundscape.presentation.screens.sounds

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.Sound
import kotlinx.android.synthetic.main.item_sound.view.*


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
        holder.itemView.itemSoundStopBtn.invisible()
        holder.itemView.titleTextView.text = list.title
        holder.itemView.lengthTextView.text = list.length + " sec"

        //Play button
        holder.itemView.itemSoundPlayBtn.setOnClickListener{

            Log.d("Clicked position :", position.toString())
            hidePlay(holder)
            soundViewModel.playSound(list.downloadLink)

            //Change the button back to play button when sound has been played
            Handler().postDelayed(({
                showPlay(holder)
            }), list.length.toLong() * 1000)
        }

        //Stop button
        holder.itemView.itemSoundStopBtn.setOnClickListener{
            Log.d("Clicked position :", position.toString())
            showPlay(holder)
            soundViewModel.stopSound()
        }
    }

    private fun hidePlay(holder: ViewHolder){
        holder.itemView.itemSoundPlayBtn.invisible()
        holder.itemView.itemSoundStopBtn.visible()
    }

    private fun showPlay(holder: ViewHolder){
        holder.itemView.itemSoundPlayBtn.visible()
        holder.itemView.itemSoundStopBtn.invisible()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}