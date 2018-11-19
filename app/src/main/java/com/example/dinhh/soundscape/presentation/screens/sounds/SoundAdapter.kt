package com.example.dinhh.soundscape.presentation.screens.sounds

import android.os.CountDownTimer
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
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.concurrent.timer
import android.support.v4.os.HandlerCompat.postDelayed



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

        holder.itemView.itemSoundPlayBtn.setOnClickListener{

            Log.d("Clicked position :", position.toString())
            holder.itemView.itemSoundPlayBtn.invisible()
            holder.itemView.itemSoundStopBtn.visible()
            soundViewModel.playSound(list.downloadLink)

            Handler().postDelayed(({
                holder.itemView.itemSoundStopBtn.invisible()
                holder.itemView.itemSoundPlayBtn.visible()
            }), list.length.toLong() * 1000)
        }

        holder.itemView.itemSoundStopBtn.setOnClickListener{
            Log.d("Clicked position :", position.toString())
            holder.itemView.itemSoundStopBtn
            holder.itemView.itemSoundPlayBtn.visible()
            soundViewModel.stopSound()
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}