package com.example.data.database

import androidx.room.TypeConverter
import com.example.domain.model.ui.Device

class Converters {

    @TypeConverter
    fun toDeviceMode(value: String) = enumValueOf<Device.DeviceMode>(value)

    @TypeConverter
    fun fromDeviceMode(value: Device.DeviceMode) = value.name
}