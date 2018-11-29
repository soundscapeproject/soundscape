package com.example.dinhh.soundscape.presentation.screens.mixer

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.SoundscapeItem
import kotlinx.android.synthetic.main.item_mixer.view.*

class MixerAdapter(private val items: MutableList<SoundscapeItem>): RecyclerView.Adapter<ViewHolder>(){
    private val handler = Handler()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_mixer, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.titleTextView.text = items[position].title
        holder.itemView.lengthTextView.text = "${items[position].length} sec"
        holder.itemView.itemSoundStopBtn.invisible()


        holder.itemView.itemSoundPlayBtn.setOnClickListener{
            items[position].sound.start()
            showStop(holder, items[position].length.toLong())
        }
        holder.itemView.itemSoundStopBtn.setOnClickListener{
            items[position].sound.pause()
            items[position].sound.seekTo(0)
            showPlay(holder)
        }
    }

    // Changes play button to stop button for the duration of the sound
    private fun showStop(holder: ViewHolder, length: Long){
        holder.itemView.itemSoundPlayBtn.invisible()
        holder.itemView.itemSoundStopBtn.visible()
        handler.postDelayed({ showPlay(holder) },length * 1000)
    }

    // Changes stop button to play button and clears the possible remaining time from the handler
    private fun showPlay(holder: ViewHolder){
        holder.itemView.itemSoundPlayBtn.visible()
        holder.itemView.itemSoundStopBtn.invisible()
        handler.removeCallbacksAndMessages(null)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}