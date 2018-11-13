package com.example.dinhh.soundscape.data.entity

import com.google.gson.annotations.SerializedName

enum class SoundType(val description: String) {

    @SerializedName("soundscapes")
    NATURE("Soundscapes"),

    @SerializedName("ambience")
    HUMAN("Ambience"),

    @SerializedName("effects")
    MACHINE("Effects");

    companion object {
        fun getTypeByName(name: String) = valueOf(name.toUpperCase())
    }
}