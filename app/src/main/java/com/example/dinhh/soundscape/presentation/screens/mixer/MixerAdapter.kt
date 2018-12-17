package com.example.dinhh.soundscape.presentation.screens.mixer

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    fun onChangeVolume(layoutPosition: Int, progress: Int)

}

class MixerAdapter(
    private val items: MutableList<SoundscapeItem>,
    private val mListener: MixerAdapterViewHolderClicks
): RecyclerView.Adapter<MixerAdapter.ViewHolder>(){

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
        val mixerItem = holder.itemView
        val currentSound = items[position]

        setColor(holder,position)
        setPlayPauseButton(holder, position)
        mixerItem.txtTitle.text = currentSound.title
        mixerItem.txtLength.text = "${currentSound.length} sec"

        mixerItem.volumeSeekBar.progress = currentSound.volume

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

    class ViewHolder (view: View,  mListener: MixerAdapterViewHolderClicks) : RecyclerView.ViewHolder(view) {
        val itemSoundPlayBtn: ImageButton = view.findViewById(R.id.btnItemSoundPlay)
        val removeSoundBtn: ImageButton = view.findViewById(R.id.btnRemoveSound)
        val itemSoundStopBtn: ImageButton = view.findViewById(R.id.btnItemSoundStop)
        val mixerItem: ConstraintLayout = view.findViewById(R.id.mixerItem)
        val toggleLoopOn: Button = view.findViewById(R.id.btnLoopToggleOn)
        val toggleLoopOff: Button = view.findViewById(R.id.btnLoopToggleOff)
        val volumeSeekBar: SeekBar = view.findViewById(R.id.volumeSeekBar)

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

            volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                var seekBarProgress: Int = 0
                @SuppressLint("ShowToast")
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    seekBarProgress = progress
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    mListener.onChangeVolume(layoutPosition, seekBarProgress)
                }

            })
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