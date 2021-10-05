package com.example.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.ui.Device
import com.example.domain.model.ui.Device.DeviceMode
import com.example.domain.model.ui.ProductType

@Entity
data class LightDeviceEntity(
    @PrimaryKey val id: Long,
    val deviceName: String,
    val productType: ProductType,
    val intensity: Int,
    val mode: DeviceMode
)


@Entity
data class HeaterDeviceEntity(
    @PrimaryKey val id: Long,
    val deviceName: String,
    val mode: DeviceMode,
    val productType: ProductType,
    val temperature: Double
)

@Entity
data class RollerShutterDeviceEntity(
    @PrimaryKey val id: Long,
    val deviceName: String,
    val productType: ProductType,
    val position: Int
)

fun Device.LightDevice.toLightDeviceEntity(): LightDeviceEntity {
    return LightDeviceEntity(
        id = id,
        deviceName = deviceName,
        productType = productType,
        intensity = intensity,
        mode = mode
    )
}

fun Device.HeaterDevice.toHeaterDeviceEntity(): HeaterDeviceEntity {
    return HeaterDeviceEntity(
        id = id,
        deviceName = deviceName,
        productType = productType,
        temperature = temperature,
        mode = mode
    )
}

fun Device.RollerShutterDevice.toRollerShutterDeviceEntity(): RollerShutterDeviceEntity {
    return RollerShutterDeviceEntity(
        id = id,
        deviceName = deviceName,
        productType = productType,
        position = position
    )
}

fun LightDeviceEntity.toLightDevice(): Device.LightDevice {
    return Device.LightDevice(
        id = id,
        deviceName = deviceName,
        productType = productType,
        intensity = intensity,
        mode = mode
    )
}

fun HeaterDeviceEntity.toHeaterDevice(): Device.HeaterDevice {
    return Device.HeaterDevice(
        id = id,
        deviceName = deviceName,
        productType = productType,
        temperature = temperature,
        mode = mode
    )
}

fun RollerShutterDeviceEntity.toRollerShutterDevice(): Device.RollerShutterDevice {
    return Device.RollerShutterDevice(
        id = id,
        deviceName = deviceName,
        productType = productType,
        position = position
    )
}