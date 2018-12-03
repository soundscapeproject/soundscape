package com.example.dinhh.soundscape.presentation.screens.library

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.gone
import com.example.dinhh.soundscape.common.visible
import com.example.dinhh.soundscape.presentation.screens.savedrecord.SavedRecord
import kotlinx.android.synthetic.main.item_sound.view.*


interface SavedRecordAdapterViewHolderClicks {

    fun onPlayPauseToggle(layoutPosition: Int)
}


class SavedRecordAdapter(
    private val records: MutableList<SavedRecord>,
    private val mListener: SavedRecordAdapterViewHolderClicks
): RecyclerView.Adapter<SavedRecordAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_sound, parent, false)
        return ViewHolder(cellForRow, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sound = records[position]
        holder.itemView.titleTextView.text = sound.title
        holder.itemView.lengthTextView.text = sound.length.toString() + " sec"
    }

    fun replaceData(localRecords: MutableList<SavedRecord>) {
        this.records.clear()
        this.records.addAll(localRecords)
        notifyDataSetChanged()
    }

    fun getData(): MutableList<SavedRecord> {
        return this.records
    }

    class ViewHolder (view: View, mListener: SavedRecordAdapterViewHolderClicks) : RecyclerView.ViewHolder(view) {

        val itemSoundPlayBtn: ImageButton = view.findViewById(R.id.itemSoundPlayBtn)
        val itemSoundStopBtn: ImageButton = view.findViewById(R.id.itemSoundStopBtn)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val lengthTextView: TextView = view.findViewById(R.id.lengthTextView)

        init {
            itemSoundPlayBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
            }

            itemSoundStopBtn.setOnClickListener {
                mListener.onPlayPauseToggle(this.layoutPosition)
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
