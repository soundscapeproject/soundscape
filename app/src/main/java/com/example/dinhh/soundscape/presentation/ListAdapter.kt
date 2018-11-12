package com.example.dinhh.soundscape.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dinhh.soundscape.R

class ListAdapter (context: Context, private val listItems: MutableList<ListItem>): BaseAdapter() {


    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // How many rows
    override fun getCount(): Int {
        return listItems.size
    }

    override fun getItem(position: Int): Any {
        return listItems[position]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_library, p2, false)
        val thisCategory = listItems[position]

        val tv = rowView.findViewById(R.id.categoryTextView) as TextView
        tv.text = thisCategory.name

        return rowView
    }
}