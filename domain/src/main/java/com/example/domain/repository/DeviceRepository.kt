package com.example.domain.repository

import com.example.domain.model.ui.Device
import com.example.domain.model.ui.ProductType
import com.example.domain.util.DeviceFilter

interface DeviceRepository {

    suspend fun getDevicesList(deviceFilter: DeviceFilter): List<Device>

    suspend fun getDevice(productType: ProductType, deviceId: Long): Device

    suspend fun updateDevice(device: Device)
}