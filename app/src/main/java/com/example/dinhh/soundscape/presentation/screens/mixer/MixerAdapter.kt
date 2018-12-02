package com.example.dinhh.soundscape.presentation.screens.mixer

import android.content.Context
import android.media.AudioManager
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.entity.SoundscapeItem
import kotlinx.android.synthetic.main.item_mixer.view.*
import android.content.Context.AUDIO_SERVICE
import android.support.v4.content.ContextCompat.getSystemService



class MixerAdapter(private val items: MutableList<SoundscapeItem>): RecyclerView.Adapter<ViewHolder>(){
    private val handler = Handler()


    companion object {
        lateinit var category: String
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_mixer, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Set the default values for each sound item
        setColor(holder,position)
        items[position].sound.setVolume(items[position].volume.toFloat(), items[position].volume.toFloat())
        holder.itemView.volumeSeekBar.progress = items[position].volume
        holder.itemView.volumeTextView.text = "Volume: ${items[position].volume}"
        holder.itemView.titleTextView.text = items[position].title
        holder.itemView.lengthTextView.text = "${items[position].length} sec"
        holder.itemView.itemSoundStopBtn.invisible()


        //Listeners:

        //Play button for individual sound
        holder.itemView.itemSoundPlayBtn.setOnClickListener{
            items[position].sound.start()
            showStop(holder, items[position].length.toLong())
        }

        //Stop button for individual sound
        holder.itemView.itemSoundStopBtn.setOnClickListener{
            items[position].sound.pause()
            items[position].sound.seekTo(0)
            showPlay(holder)
        }

        //Remove selected sound from the soundscape
        holder.itemView.removeSoundBtn.setOnClickListener {
            Model.selectedSounds.removeAt(position)
            notifyDataSetChanged()
        }

        //Volume slider for individual sound
        holder.itemView.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                holder.itemView.volumeTextView.text = "Volume: $i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                items[position].volume = seekBar.progress
            }
        })
    }

    // Sets the color of the individual sound item according to the category it belongs
    private fun setColor(holder: ViewHolder, position: Int){
        if(items[position].category == "nature"){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_nature)
        }
        if(items[position].category == "human"){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_human)
        }
        if(items[position].category == "machine"){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_machine)
        }
        if(items[position].category == "story"){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_story)
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