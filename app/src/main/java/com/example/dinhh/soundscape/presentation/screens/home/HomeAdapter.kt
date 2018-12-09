package com.example.dinhh.soundscape.presentation.screens.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.presentation.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_soundscapes.view.*

interface HomeAdapterViewHolderClicks {

    fun uploadSound(layoutPosition: Int)
}

class HomeAdapter(
    private val localSoundscapes: MutableList<LocalSoundscape>,
    private val mListener: HomeAdapterViewHolderClicks
) : BaseRecyclerViewAdapter<HomeAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return localSoundscapes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_soundscapes, parent, false)
        return ViewHolder(cellForRow, mListener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)

        val vh = viewHolder as HomeAdapter.ViewHolder

        val localSoundscape = localSoundscapes[i]
        vh.itemView.txtName.text = localSoundscape.title
    }

    fun removeAt(position: Int) {
        localSoundscapes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun replaceData(localSoundscapes: List<LocalSoundscape>) {
        this.localSoundscapes.clear()
        this.localSoundscapes.addAll(localSoundscapes)
        notifyDataSetChanged()
    }

    fun getData(): List<LocalSoundscape> {
        return this.localSoundscapes
    }

    class ViewHolder(view: View, mListener: HomeAdapterViewHolderClicks) :
        RecyclerView.ViewHolder(view) {

        val txtName: TextView = view.findViewById(R.id.txtName)
    }
}