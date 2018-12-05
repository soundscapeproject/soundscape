package com.example.dinhh.soundscape.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.dinhh.soundscape.data.local.DatabaseConfig

@Entity(tableName = DatabaseConfig.SOUNDSCAPE_TABLE_NAME)
data class LocalSoundscape(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sound_id")
    val soundId: Long?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "soundscapes")
    val soundScapeList: List<SoundScape>,
    val isUploaded: Boolean = false,
    @ColumnInfo(name = "favorited")
    val isFavorited: Boolean = false
)