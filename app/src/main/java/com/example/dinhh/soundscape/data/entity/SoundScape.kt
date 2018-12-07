package com.example.dinhh.soundscape.data.entity

data class SoundScape(
    val title: String,
    val length: String,
    val category: String,
    var source: String,
    var volume: Int
)