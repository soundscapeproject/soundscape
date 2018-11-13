package com.example.dinhh.soundscape.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.dinhh.soundscape.data.local.DatabaseConfig

@Entity(tableName = DatabaseConfig.RECORD_TABLE_NAME)
data class LocalRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sound_id")
    val soundId: Long?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "length_sec")
    val length_sec: Long
)