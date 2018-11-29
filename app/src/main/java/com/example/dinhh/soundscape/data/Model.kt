package com.example.dinhh.soundscape.data

import android.media.MediaPlayer
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.data.entity.SoundscapeItem
import com.example.dinhh.soundscape.presentation.ListItem

object Model {
    val category: MutableList<ListItem> = mutableListOf()
    var sounds:  List<List<Sound>> = mutableListOf()
    var selectedSounds: MutableList<SoundscapeItem> = mutableListOf()


    init {
        category.add(ListItem("Nature"))
        category.add(ListItem("Human"))
        category.add(ListItem("Machine"))
        category.add(ListItem("Story"))
    }
}