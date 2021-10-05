package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.SmartDeviceDao
import com.example.data.database.entity.HeaterDeviceEntity
import com.example.data.database.entity.LightDeviceEntity
import com.example.data.database.entity.RollerShutterDeviceEntity

@Database(
    entities = [LightDeviceEntity::class, HeaterDeviceEntity::class, RollerShutterDeviceEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smartDeviceDao(): SmartDeviceDao
}