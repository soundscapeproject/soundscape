package com.example.dinhh.soundscape.presentation.screens.entity

import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.RemoteSound

data class DisplaySound(
    val title: String,
    val category: String,
    val downloadLink: String,
    val isUploaded: Boolean = false,
    val isPlaying: Boolean = false,
    val isFavorite: Boolean = false
) {

    companion object {

        fun remoteSoundToDisplaySound(remoteSound: RemoteSound): DisplaySound {

            return DisplaySound(
                remoteSound.title,
                remoteSound.category,
                remoteSound.downloadLink,
                true
            )
        }

        fun localRecordToDisplaySound(localRecord: LocalRecord): DisplaySound {

            return DisplaySound(
                localRecord.title,
                localRecord.category,
                localRecord.url,
                false
            )
        }
    }
}