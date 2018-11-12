package com.example.dinhh.soundscape.data

import com.example.dinhh.soundscape.presentation.ListItem

object Model {
    val category: kotlin.collections.MutableList<ListItem> = java.util.ArrayList()
    val sounds: kotlin.collections.MutableList<ListItem> = java.util.ArrayList()


    init {
        category.add(ListItem("Nature"))
        category.add(ListItem("Human"))
        category.add(ListItem("Machine"))
        category.add(ListItem("Story"))
    }
}