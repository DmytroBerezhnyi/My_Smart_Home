package com.example.data.net.repository

import android.content.Context
import com.example.data.database.dao.SmartDeviceDao
import com.example.data.database.entity.*
import com.example.domain.extensions.iterator
import com.example.domain.extensions.readTextFromAsset
import com.example.domain.model.ui.Device
import com.example.domain.model.ui.Device.*
import com.example.domain.model.ui.ProductType
import com.example.domain.repository.DeviceRepository
import com.example.domain.util.DeviceFilter
import org.json.JSONObject
import timber.log.Timber

class DeviceDataRepository(
    private val context: Context,
    private val deviceDao: SmartDeviceDao
) : DeviceRepository {

    override suspend fun getDevicesList(deviceFilter: DeviceFilter): List<Device> {
        val deviceList = mutableListOf<Device>().apply {
            addAll(deviceDao.getLightDeviceList().map { it.toLightDevice() })
            addAll(deviceDao.getHeaterDeviceList().map { it.toHeaterDevice() })
            addAll(deviceDao.getRollerShutterDeviceList().map { it.toRollerShutterDevice() })
            sortBy { it.id }
        }

        if (deviceList.isEmpty()) {
            val jsonString = context.readTextFromAsset("data.json")
            deviceList.addAll(jsonString.jsonToDeviceList())
            deviceList.forEach {
                when (it) {
                    is LightDevice -> deviceDao.insert(it.toLightDeviceEntity())
                    is HeaterDevice -> deviceDao.insert(it.toHeaterDeviceEntity())
                    is RollerShutterDevice -> deviceDao.insert(it.toRollerShutterDeviceEntity())
                }
            }
        }

        return when (deviceFilter) {
            DeviceFilter.All -> deviceList
            DeviceFilter.Light -> deviceList.filter { it.productType == ProductType.Light }
            DeviceFilter.Heater -> deviceList.filter { it.productType == ProductType.Heater }
            DeviceFilter.RollerShutter -> deviceList.filter { it.productType == ProductType.RollerShutter }
        }
    }

    override suspend fun getDevice(productType: ProductType, deviceId: Long): Device {
        return when (productType) {
            ProductType.Light -> deviceDao.getLightDeviceById(deviceId).toLightDevice()
            ProductType.Heater -> deviceDao.getHeaterDeviceById(deviceId).toHeaterDevice()
            ProductType.RollerShutter -> deviceDao.getRollerShutterDeviceById(deviceId)
                .toRollerShutterDevice()
        }
    }

    override suspend fun updateDevice(device: Device) {
        when (device) {
            is LightDevice -> deviceDao.update(device.toLightDeviceEntity())
            is HeaterDevice -> deviceDao.update(device.toHeaterDeviceEntity())
            is RollerShutterDevice -> deviceDao.update(device.toRollerShutterDeviceEntity())
        }
    }

    private fun String.jsonToDeviceList(): List<Device> {
        return mutableListOf<Device>().apply {
            val jsonArray = JSONObject(this@jsonToDeviceList).getJSONArray("devices")
            jsonArray.iterator<JSONObject>().forEach { jsonObject ->

                val id = jsonObject.getLong("id")
                val deviceName = jsonObject.getString("deviceName")

                when (jsonObject.getString("productType")) {
                    ProductType.Light.value -> {
                        add(
                            LightDevice(
                                id = id,
                                deviceName = deviceName,
                                productType = ProductType.Light,
                                intensity = jsonObject.getInt("intensity"),
                                mode = when (jsonObject.getString("mode")) {
                                    DeviceMode.ON.value -> DeviceMode.ON
                                    else -> DeviceMode.OFF
                                }
                            )
                        )
                    }
                    ProductType.Heater.value -> {
                        add(
                            HeaterDevice(
                                id = id,
                                deviceName = deviceName,
                                mode = when (jsonObject.getString("mode")) {
                                    DeviceMode.ON.value -> DeviceMode.ON
                                    else -> DeviceMode.OFF
                                },
                                productType = ProductType.Heater,
                                temperature = jsonObject.getDouble("temperature")
                            )
                        )
                    }
                    ProductType.RollerShutter.value -> {
                        add(
                            RollerShutterDevice(
                                id = id,
                                deviceName = deviceName,
                                productType = ProductType.RollerShutter,
                                position = jsonObject.getInt("position")
                            )
                        )
                    }
                }
            }
        }
    }
}