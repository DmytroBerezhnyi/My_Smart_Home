package com.example.mysmarthome.ui.fragment.device_light

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.net.repository.DeviceDataRepository
import com.example.domain.model.ui.Device
import com.example.domain.model.ui.Device.LightDevice
import com.example.domain.model.ui.ProductType
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseViewModel
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.base.model.SnackbarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceLightViewModel @Inject constructor(private val deviceDataRepository: DeviceDataRepository) :
    BaseViewModel() {

    val lightDevice = MutableLiveData<LightDevice>()

    fun loadLightDevice(heaterDeviceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            lightDevice.postValue(
                deviceDataRepository.getDevice(
                    ProductType.Light,
                    heaterDeviceId
                ) as LightDevice
            )
        }
    }

    fun updateLightDeviceData(lightDevice: LightDevice) {
        launchAsync(
            block = {
            deviceDataRepository.updateDevice(lightDevice)
        },
            onSuccess = {
                snackLiveEvent.postValue(SnackbarModel(idResMessage = R.string.light_device_data_updated))
                navEvent.postValue(NavigationModel(popBack = true))
            })
    }
}