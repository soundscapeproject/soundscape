package com.example.dinhh.soundscape.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.dinhh.soundscape.data.local.DatabaseConfig
import com.example.dinhh.soundscape.presentation.screens.entity.DisplaySound

@Entity(tableName = DatabaseConfig.RECORD_TABLE_NAME)
data class LocalRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sound_id")
    val soundId: Long?,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "length_sec")
    val length_sec: Long,
    @ColumnInfo(name = "uploaded")
    var isUploaded: Boolean = false,
    @ColumnInfo(name = "favorited")
    var isFavorited: Boolean = false,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {

        fun displaySoundToLocalRecord(displaySound: DisplaySound): LocalRecord {

            return LocalRecord(
                displaySound.id,
                displaySound.title,
                displaySound.category,
                SoundType.EFFECTS.description,
                displaySound.downloadLink,
                displaySound.length!!.toLong(),
                displaySound.isUploaded,
                displaySound.isFavorite,
                displaySound.createdAt!!
            )
        }
    }
}