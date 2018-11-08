package com.example.dinhh.soundscape.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.dinhh.soundscape.data.database.DatabaseConfig

@Entity(tableName = DatabaseConfig.SOUNDSCAPE_TABLE_NAME)
data class SoundScapeRecord(
    @PrimaryKey
    @ColumnInfo(name = "sound_id")
    val title: Int,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "length_sec")
    val length_sec: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Int,
    @ColumnInfo(name = "is_recording")
    val isRecording: String,
    @ColumnInfo(name = "filename")
    val filename: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "file_ext")
    val fileExt: String
)