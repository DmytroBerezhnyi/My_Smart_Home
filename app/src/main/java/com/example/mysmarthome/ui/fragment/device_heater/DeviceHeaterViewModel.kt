package com.example.mysmarthome.ui.fragment.device_heater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.net.repository.DeviceDataRepository
import com.example.domain.model.ui.Device.HeaterDevice
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
class DeviceHeaterViewModel @Inject constructor(private val deviceDataRepository: DeviceDataRepository) :
    BaseViewModel() {

    val heaterDevice = MutableLiveData<HeaterDevice>()

    fun loadHeaterDevice(heaterDeviceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            heaterDevice.postValue(
                deviceDataRepository.getDevice(
                    ProductType.Heater,
                    heaterDeviceId
                ) as HeaterDevice
            )
        }
    }

    fun updateHeaterDeviceData(heaterDevice: HeaterDevice) {
        launchAsync(block = {
            deviceDataRepository.updateDevice(heaterDevice)
        },
            onSuccess = {
                snackLiveEvent.postValue(SnackbarModel(idResMessage = R.string.heater_device_data_updated))
                navEvent.postValue(NavigationModel(popBack = true))
            })
    }
}