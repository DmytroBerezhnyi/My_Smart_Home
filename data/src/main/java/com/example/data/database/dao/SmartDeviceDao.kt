package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.database.entity.HeaterDeviceEntity
import com.example.data.database.entity.LightDeviceEntity
import com.example.data.database.entity.RollerShutterDeviceEntity

@Dao
interface SmartDeviceDao {

    @Query("SELECT COUNT(*) FROM LightDeviceEntity, HeaterDeviceEntity, RollerShutterDeviceEntity")
    suspend fun getCountOfRows(): Int

    @Query("SELECT * FROM LightDeviceEntity")
    suspend fun getLightDeviceList(): List<LightDeviceEntity>

    @Query("SELECT * FROM HeaterDeviceEntity")
    suspend fun getHeaterDeviceList(): List<HeaterDeviceEntity>

    @Query("SELECT * FROM RollerShutterDeviceEntity")
    suspend fun getRollerShutterDeviceList(): List<RollerShutterDeviceEntity>

    @Insert
    suspend fun insert(lightDeviceEntity: LightDeviceEntity)

    @Insert
    suspend fun insert(heaterDeviceEntity: HeaterDeviceEntity)

    @Insert
    suspend fun insert(rollerShutterDeviceEntity: RollerShutterDeviceEntity)
}