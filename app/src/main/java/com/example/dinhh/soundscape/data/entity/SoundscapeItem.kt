package com.example.dinhh.soundscape.data.entity

import android.media.MediaPlayer

data class SoundscapeItem(
    val title: String,
    val length: Int,
    val sound: MediaPlayer,
    val category: String,
    var volume: Int
) {
}