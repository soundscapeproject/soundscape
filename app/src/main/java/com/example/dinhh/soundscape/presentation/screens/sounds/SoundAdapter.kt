package com.example.dinhh.soundscape.presentation.screens.sounds

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.invisible
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.presentation.screens.entity.DisplaySound
import kotlinx.android.synthetic.main.item_sound.view.*

interface SoundAdapterViewHolderClicks {

    fun onPlayPauseToggle(layoutPosition: Int)

    fun addSoundToSoundscape(layoutPosition: Int)
}

class SoundAdapter(
    private val displaySounds: MutableList<DisplaySound>,
    private val mListener: SoundAdapterViewHolderClicks
) : RecyclerView.Adapter<SoundAdapter.ViewHolder>() {


    companion object {
        var selectButtonIsVisible = false
    }

    override fun getItemCount(): Int {
        return displaySounds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sound, parent, false)
        return ViewHolder(cellForRow, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (selectButtonIsVisible) {
            holder.itemView.selectSoundBtn.visible()
        }
        val sound = displaySounds[position]
        holder.itemView.itemSoundStopBtn.invisible()
        holder.itemView.titleTextView.text = sound.title

        if (!sound.length.isNullOrEmpty()) {
            holder.itemView.lengthTextView.text = "${sound.length} sec"
        }
    }

    fun removeAt(position: Int) {
        displaySounds.removeAt(position)
        notifyItemRemoved(position)
    }

    fun replaceData(displaySounds: List<DisplaySound>) {
        this.displaySounds.clear()
        this.displaySounds.addAll(displaySounds)
        notifyDataSetChanged()
    }

    fun getData(): List<DisplaySound> {
        return this.displaySounds
    }

    class ViewHolder(view: View, mListener: SoundAdapterViewHolderClicks) :
        RecyclerView.ViewHolder(view) {

        val itemSoundPlayBtn: ImageButton = view.findViewById(R.id.itemSoundPlayBtn)
        val itemSoundStopBtn: ImageButton = view.findViewById(R.id.itemSoundStopBtn)
        val itemSoundAddBtn: ImageButton = view.findViewById(R.id.selectSoundBtn)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val lengthTextView: TextView = view.findViewById(R.id.lengthTextView)

        init {
            itemSoundPlayBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
            }

            itemSoundStopBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
            }

            itemSoundAddBtn.setOnClickListener {
                mListener.addSoundToSoundscape(this.layoutPosition)

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