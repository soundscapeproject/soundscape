package com.example.dinhh.soundscape.presentation.screens.library

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.base.BaseRecyclerViewAdapter


class LibararyAdapter(val myLibraryTitle: List<String>): BaseRecyclerViewAdapter<LibararyAdapter.LibraryViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_library, parent, false)
        return LibraryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return myLibraryTitle.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)

        val vh = viewHolder as LibraryViewHolder
        val title = myLibraryTitle[i]

        vh.txtLibraryTitle.text = title
    }

    class LibraryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var txtLibraryTitle: TextView

        init {
            txtLibraryTitle = view.findViewById(R.id.txtLibraryTitle)
        }
    }

}