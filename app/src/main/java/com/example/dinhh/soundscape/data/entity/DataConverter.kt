package com.example.dinhh.soundscape.data.entity

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromSoundscapeList(soundScapeList: List<SoundScape>): String {

            val gson = Gson()
            val type = object : TypeToken<List<SoundScape>>() {}.type
            val json = gson.toJson(soundScapeList, type)
            return json
        }

        @TypeConverter
        @JvmStatic
        fun toSoundscapeList(soundScapeListString: String): List<SoundScape> {

            val gson = Gson()
            val type = object : TypeToken<List<SoundScape>>() {}.type
            val soundScapeList = gson.fromJson<List<SoundScape>>(soundScapeListString, type)
            return soundScapeList
        }
    }
}