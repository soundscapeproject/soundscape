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
import com.example.dinhh.soundscape.presentation.base.BaseRecyclerViewAdapter
import com.example.dinhh.soundscape.presentation.screens.entity.DisplaySound
import kotlinx.android.synthetic.main.item_sound.view.*

interface SoundAdapterViewHolderClicks {

    fun onPlayPauseToggle(layoutPosition: Int)

    fun uploadSound(layoutPosition: Int)
}

class SoundAdapter(
    private val displaySounds: MutableList<DisplaySound>,
    private val mListener: SoundAdapterViewHolderClicks
) : BaseRecyclerViewAdapter<SoundAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return displaySounds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sound, parent, false)
        return ViewHolder(cellForRow, mListener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)

        val vh = viewHolder as SoundAdapter.ViewHolder

        val sound = displaySounds[i]
        vh.itemView.btnItemSoundStop.invisible()
        vh.itemView.txtTitle.text = sound.title

        if (!sound.length.isNullOrEmpty()) {
            vh.itemView.txtLength.text = "${sound.length} sec"
        }

        if (sound.isUploaded) {
            vh.itemView.btnUpload.gone()
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

        val itemSoundPlayBtn: ImageButton = view.findViewById(R.id.btnItemSoundPlay)
        val itemSoundStopBtn: ImageButton = view.findViewById(R.id.btnItemSoundStop)
        val btnUpload: ImageButton = view.findViewById(R.id.btnUpload)
        val titleTextView: TextView = view.findViewById(R.id.txtTitle)
        val lengthTextView: TextView = view.findViewById(R.id.txtLength)

        init {
            itemSoundPlayBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
            }

            itemSoundStopBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
            }

            btnUpload.setOnClickListener {
                mListener.uploadSound(this.layoutPosition)
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