package com.example.dinhh.soundscape.presentation.screens.entity

import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.RemoteSound

data class DisplaySound(
    val id: Long,
    val title: String,
    val category: String,
    val downloadLink: String,
    val length: String?,
    val createdAt: Long?,
    val isUploaded: Boolean = false,
    var isPlaying: Boolean = false,
    val isFavorite: Boolean = false
) {

    companion object {

        fun remoteSoundToDisplaySound(remoteSound: RemoteSound): DisplaySound {

            return DisplaySound(
                0,
                remoteSound.title,
                remoteSound.category,
                remoteSound.downloadLink,
                remoteSound.length,
                null,
                true
            )
        }

        fun localRecordToDisplaySound(localRecord: LocalRecord): DisplaySound {

            return DisplaySound(
                localRecord.soundId!!,
                localRecord.title,
                localRecord.category,
                localRecord.url,
                localRecord.length_sec.toString(),
                localRecord.createdAt,
                false
            )
        }
    }
}