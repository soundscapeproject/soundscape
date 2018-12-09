package com.example.dinhh.soundscape.data.entity

import com.google.gson.annotations.SerializedName

enum class SoundCategory(val description: String) {

    @SerializedName("nature")
    NATURE("Nature"),

    @SerializedName("human")
    HUMAN("Human"),

    @SerializedName("machine")
    MACHINE("Machine"),

    @SerializedName("story")
    STORY("Story"),

    @SerializedName("record")
    RECORD("Record");

    companion object {
        fun getCategoryByName(name: String) = valueOf(name.toUpperCase())
    }
}