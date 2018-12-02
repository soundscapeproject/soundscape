package com.example.dinhh.soundscape.presentation.screens.library

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.presentation.base.BaseRecyclerViewAdapter


class SavedRecordAdapter(val localRecords: MutableList<LocalRecord>): BaseRecyclerViewAdapter<SavedRecordAdapter.SavedRecordViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecordAdapter.SavedRecordViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_record, parent, false)
        return SavedRecordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return localRecords.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val vh = holder as SavedRecordViewHolder
        val localRecord = localRecords[position]

        vh.txtName.text = localRecord.title
    }

    fun replaceData(localRecords: List<LocalRecord>) {
        this.localRecords.clear()
        this.localRecords.addAll(localRecords)
        notifyDataSetChanged()
    }

    fun addData(localRecords: List<LocalRecord>) {
        this.localRecords.addAll(localRecords)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.localRecords.clear()
        notifyDataSetChanged()
    }

    class SavedRecordViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val btnUpload: ImageButton = view.findViewById(R.id.btnUpload)
    }

}