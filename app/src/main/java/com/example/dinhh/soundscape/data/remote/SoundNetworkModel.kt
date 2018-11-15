package com.example.dinhh.soundscape.data.remote

import com.google.gson.annotations.SerializedName

object SoundNetworkModel {
    data class Response(@SerializedName("Title")
                        val title: String)
}