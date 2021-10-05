package com.example.domain.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Device(
    open val id: Long,
    open val deviceName: String,
    open val productType: ProductType
) {

    @Parcelize
    data class LightDevice(
        override val id: Long,
        override val deviceName: String,
        override val productType: ProductType,
        val intensity: Int,
        val mode: DeviceMode
    ) : Device(id, deviceName, productType), Parcelable

    @Parcelize
    data class HeaterDevice(
        override val id: Long,
        override val deviceName: String,
        val mode: DeviceMode,
        override val productType: ProductType,
        val temperature: Double
    ) : Device(id, deviceName, productType), Parcelable

    @Parcelize
    data class RollerShutterDevice(
        override val id: Long,
        override val deviceName: String,
        override val productType: ProductType,
        val position: Int
    ) : Device(id, deviceName, productType), Parcelable

    enum class DeviceMode(val value: String) {
        ON("ON"),
        OFF("OFF")
    }
}