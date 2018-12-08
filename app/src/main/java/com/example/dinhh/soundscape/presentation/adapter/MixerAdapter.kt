package com.example.dinhh.soundscape.presentation.adapter

import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.data.entity.SoundCategory
import com.example.dinhh.soundscape.device.SoundscapeItem
import kotlinx.android.synthetic.main.item_mixer.view.*

interface MixerAdapterViewHolderClicks {

    fun onPlaySingleSoundScape(layoutPosition: Int)

    fun onStopSingleSoundScape(layoutPosition: Int)

    fun onRemoveSingleSoundScape(layoutPosition: Int)

    fun onLoopSingleSound(layoutPosition: Int, isLooping: Boolean)

}

class MixerAdapter(
    private val items: MutableList<SoundscapeItem>,
    private val mListener: MixerAdapterViewHolderClicks
): RecyclerView.Adapter<MixerAdapter.ViewHolder>(){
    private val handler = Handler()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_mixer, parent, false)
        return ViewHolder(cellForRow, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setupMixerItem(holder, position)
    }

    private fun setupMixerItem(holder: ViewHolder, position: Int){
        //Set the default values for each sound item
//        val maxVolume = 100.0
        val mixerItem = holder.itemView
        val currentSound = items[position]

        setColor(holder,position)
        setPlayPauseButton(holder, position)
//        currentSound.sound.setVolume(currentSound.volume.toFloat(), currentSound.volume.toFloat())
//        mixerItem.volumeSeekBar.progress = currentSound.volume
        mixerItem.titleTextView.text = currentSound.title
        mixerItem.lengthTextView.text = "${currentSound.length} sec"

        //Volume slider for individual sound
//        holder.itemView.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                // Display the current progress of SeekBar
//                val log1 = (Math.log(maxVolume - i) / Math.log(maxVolume)).toFloat()
//                currentSound.sound.setVolume(1-log1,1-log1)
//            }
//            override fun onStartTrackingTouch(seekBar: SeekBar) {
//                // Do something
//            }
//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//                currentSound.volume = seekBar.progress
//            }
//        })
    }

    fun setPlayPauseButton(holder: ViewHolder, position: Int) {
        val sound = items[position]

        if (sound.isPlaying) {
            holder.itemSoundStopBtn.visible()
            holder.itemSoundPlayBtn.invisible()
        } else {
            holder.itemSoundPlayBtn.visible()
            holder.itemSoundStopBtn.invisible()
        }
    }

    fun replaceData(items:  MutableList<SoundscapeItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    // Sets the color of the individual sound item according to the category it belongs
    private fun setColor(holder: ViewHolder, position: Int){

        if(items[position].category == SoundCategory.NATURE.description.toLowerCase()){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_nature)
        }
        if(items[position].category == SoundCategory.HUMAN.description.toLowerCase()){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_human)
        }
        if(items[position].category == SoundCategory.MACHINE.description.toLowerCase()){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_machine)
        }
        if(items[position].category == SoundCategory.STORY.description.toLowerCase()){
            holder.itemView.mixerItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background_story)
        }
    }

//    // Changes play button to stop button for the duration of the sound
//    private fun showStop(holder: ViewHolder, length: Long){
//        holder.itemView.itemSoundPlayBtn.invisible()
//        holder.itemView.itemSoundStopBtn.visible()
//        handler.postDelayed({ showPlay(holder) },length * 1000)
//    }
//
//    // Changes stop button to play button and clears the possible remaining time from the handler
//    private fun showPlay(holder: ViewHolder){
//        holder.itemView.itemSoundPlayBtn.visible()
//        holder.itemView.itemSoundStopBtn.invisible()
//        handler.removeCallbacksAndMessages(null)
//    }

    class ViewHolder (view: View,  mListener: MixerAdapterViewHolderClicks) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val lengthTextView: TextView = view.findViewById(R.id.lengthTextView)
        val itemSoundPlayBtn: ImageButton = view.findViewById(R.id.itemSoundPlayBtn)
        val removeSoundBtn: ImageButton = view.findViewById(R.id.removeSoundBtn)
        val itemSoundStopBtn: ImageButton = view.findViewById(R.id.itemSoundStopBtn)
        val mixerItem: ConstraintLayout = view.findViewById(R.id.mixerItem)
        val toggleLoopOn: Button = view.findViewById(R.id.loopBtnToggleOn)
        val toggleLoopOff: Button = view.findViewById(R.id.loopBtnToggleOff)

        init {
            itemSoundPlayBtn.setOnClickListener {
                mListener.onPlaySingleSoundScape(this.layoutPosition)
            }

            itemSoundStopBtn.setOnClickListener {
                mListener.onStopSingleSoundScape(this.layoutPosition)
            }

            removeSoundBtn.setOnClickListener {
                mListener.onRemoveSingleSoundScape(this.layoutPosition)
            }

            toggleLoopOn.setOnClickListener {
                toggleLoopOff.visible()
                toggleLoopOn.invisible()
                mListener.onLoopSingleSound(this.layoutPosition, true)
            }

            toggleLoopOff.setOnClickListener {
                toggleLoopOn.visible()
                toggleLoopOff.invisible()
                mListener.onLoopSingleSound(this.layoutPosition, false)
            }
        }

        fun setPlayingState(playing: Boolean) {

            if (playing) {
                this.itemSoundPlayBtn.gone()
                this.itemSoundStopBtn.visible()
            } else {
                this.itemSoundPlayBtn.visible()
                this.itemSoundStopBtn.gone()
            }
        }
    }
}