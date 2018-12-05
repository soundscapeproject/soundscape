package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.dinhh.soundscape.data.entity.DataConverter
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.LocalSoundscape

@Database(entities = [LocalRecord::class, LocalSoundscape::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun soundscapeDao(): SoundscapeDao
}