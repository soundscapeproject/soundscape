package com.example.dinhh.soundscape.presentation.screens.mixer

import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.Model
import com.example.dinhh.soundscape.data.entity.SoundCategory
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
        setupMixerItem(holder, position)
    }

    private fun setupMixerItem(holder: ViewHolder, position: Int){
        //Set the default values for each sound item
        val maxVolume = 100.0
        val mixerItem = holder.itemView
        val currentSound = items[position]

        setColor(holder,position)
        currentSound.sound.setVolume(currentSound.volume.toFloat(), currentSound.volume.toFloat())
        mixerItem.volumeSeekBar.progress = currentSound.volume
        mixerItem.titleTextView.text = currentSound.title
        mixerItem.lengthTextView.text = "${currentSound.length} sec"
        mixerItem.itemSoundStopBtn.invisible()

        //Listeners:

        //Play button for individual sound
        holder.itemView.itemSoundPlayBtn.setOnClickListener{
            currentSound.sound.start()
            showStop(holder, currentSound.length.toLong())
        }

        //Stop button for individual sound
        holder.itemView.itemSoundStopBtn.setOnClickListener{
            currentSound.sound.pause()
            currentSound.sound.seekTo(0)
            showPlay(holder)
        }

        //Remove selected sound from the soundscape
        holder.itemView.removeSoundBtn.setOnClickListener {
            currentSound.sound.stop()
            Model.selectedSounds.removeAt(position)
            notifyDataSetChanged()
        }

        //Volume slider for individual sound
        holder.itemView.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val log1 = (Math.log(maxVolume - i) / Math.log(maxVolume)).toFloat()
                currentSound.sound.setVolume(1-log1,1-log1)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                currentSound.volume = seekBar.progress
            }
        })

    }

    // Sets the color of the individual sound item according to the category it belongs
    private fun setColor(holder: ViewHolder, position: Int){
        if(items[position].category == SoundCategory.NATURE.description){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_nature)
        }
        if(items[position].category == SoundCategory.HUMAN.description){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_human)
        }
        if(items[position].category == SoundCategory.MACHINE.description){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_machine)
        }
        if(items[position].category == SoundCategory.STORY.description){
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