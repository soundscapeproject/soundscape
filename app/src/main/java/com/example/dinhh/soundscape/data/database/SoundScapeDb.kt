package com.example.dinhh.soundscape.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dinhh.soundscape.data.entity.SoundScapeRecord

@Database(entities = [SoundScapeRecord::class], version = 1)
abstract class SoundScapeDb : RoomDatabase() {
    abstract fun soundScapeDao(): SoundScapeDb
}