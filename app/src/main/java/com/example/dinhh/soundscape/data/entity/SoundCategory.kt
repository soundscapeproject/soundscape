package com.example.dinhh.soundscape.data.entity

import com.google.gson.annotations.SerializedName

enum class SoundCategory {

    @SerializedName("nature")
    NATURE,

    @SerializedName("human")
    HUMAN,

    @SerializedName("machine")
    MACHINE,

    @SerializedName("story")
    STORY,

    @SerializedName("")
    UNKNOWN;
}