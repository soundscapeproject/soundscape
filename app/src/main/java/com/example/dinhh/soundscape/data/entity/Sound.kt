package com.example.dinhh.soundscape.data.entity

import com.google.gson.annotations.SerializedName

data class Sound(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Original filename")
    val originalFilename: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("Sound Type")
    val soundType: String,
    @SerializedName("Length (sec)")
    val length: String,
    @SerializedName("Creation date")
    val creationDate: String,
    @SerializedName("File extension")
    val fileExtension: String,
    @SerializedName("File size(KB)")
    val fileSize: String,
    @SerializedName("Created by")
    val createdBy: String,
    @SerializedName("Collection name")
    val collectionName: String,
    @SerializedName("Collection ID")
    val collectionId: String,
    @SerializedName("Download link")
    val downloadLink: String,
    var isPlaying: Boolean = false
)