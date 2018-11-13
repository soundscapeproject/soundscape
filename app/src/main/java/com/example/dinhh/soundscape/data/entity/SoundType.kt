package com.example.dinhh.soundscape.data.entity

import com.google.gson.annotations.SerializedName

enum class SoundType(val description: String) {

    @SerializedName("soundscapes")
    SOUNDSCAPE("Soundscapes"),

    @SerializedName("ambience")
    AMBIENCE("Ambience"),

    @SerializedName("effects")
    EFFECTS("Effects");

    companion object {
        fun getTypeByName(name: String) = valueOf(name.toUpperCase())
    }
}