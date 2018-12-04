package com.example.dinhh.soundscape.data.entity

data class SoundScape(
    val title: String,
    val length: Int,
    val category: String,
    var source: String,
    var volume: Int = 50
)