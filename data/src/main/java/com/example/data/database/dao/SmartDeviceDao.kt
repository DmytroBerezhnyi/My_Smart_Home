package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.database.entity.HeaterDeviceEntity
import com.example.data.database.entity.LightDeviceEntity
import com.example.data.database.entity.RollerShutterDeviceEntity

@Dao
interface SmartDeviceDao {

    @Query("SELECT * FROM LightDeviceEntity")
    suspend fun getLightDeviceList(): List<LightDeviceEntity>

    @Query("SELECT * FROM HeaterDeviceEntity")
    suspend fun getHeaterDeviceList(): List<HeaterDeviceEntity>

    @Query("SELECT * FROM RollerShutterDeviceEntity")
    suspend fun getRollerShutterDeviceList(): List<RollerShutterDeviceEntity>

    @Query("SELECT * FROM LightDeviceEntity WHERE id = :id")
    fun getLightDeviceById(id: Long): LightDeviceEntity

    @Query("SELECT * FROM HeaterDeviceEntity WHERE id = :id")
    fun getHeaterDeviceById(id: Long): HeaterDeviceEntity

    @Query("SELECT * FROM RollerShutterDeviceEntity WHERE id = :id")
    fun getRollerShutterDeviceById(id: Long): RollerShutterDeviceEntity

    @Insert
    suspend fun insert(lightDeviceEntity: LightDeviceEntity)

    @Insert
    suspend fun insert(heaterDeviceEntity: HeaterDeviceEntity)

    @Insert
    suspend fun insert(rollerShutterDeviceEntity: RollerShutterDeviceEntity)

    @Update
    suspend fun update(lightDeviceEntity: LightDeviceEntity)

    @Update
    suspend fun update(heaterDeviceEntity: HeaterDeviceEntity)

    @Update
    suspend fun update(rollerShutterDeviceEntity: RollerShutterDeviceEntity)
}