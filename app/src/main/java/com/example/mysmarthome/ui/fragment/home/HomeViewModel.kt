package com.example.mysmarthome.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.data.net.repository.DeviceDataRepository
import com.example.domain.model.ui.Device
import com.example.domain.util.DeviceFilter
import com.example.mysmarthome.base.architecture.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val deviceDataRepository: DeviceDataRepository) :
    BaseViewModel() {

    val deviceList = MutableLiveData<List<Device>>()

    fun loadDevices(deviceFilter: DeviceFilter = DeviceFilter.All) {
        launchAsync({
            deviceList.value = deviceDataRepository.getDevicesList(deviceFilter)
        })
    }
}