package com.example.dinhh.soundscape.presentation.screens.savedrecord

data class SavedRecord(
    val id: Long,
    val title: String,
    val url: String,
    val length: Long,
    val isUploaded: Boolean,
    val isFavorited: Boolean,
    var isPlaying: Boolean = false
)

