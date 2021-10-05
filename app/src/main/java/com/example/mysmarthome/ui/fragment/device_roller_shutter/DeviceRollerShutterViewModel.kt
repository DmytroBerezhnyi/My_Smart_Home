package com.example.mysmarthome.ui.fragment.device_roller_shutter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.net.repository.DeviceDataRepository
import com.example.domain.model.ui.Device
import com.example.domain.model.ui.Device.RollerShutterDevice
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
class DeviceRollerShutterViewModel @Inject constructor(private val deviceDataRepository: DeviceDataRepository) :
    BaseViewModel() {

    val rollerShutterDevice = MutableLiveData<RollerShutterDevice>()

    fun loadRollerShutterDevice(heaterDeviceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            rollerShutterDevice.postValue(
                deviceDataRepository.getDevice(
                    ProductType.RollerShutter,
                    heaterDeviceId
                ) as RollerShutterDevice
            )
        }
    }

    fun updateRollerShutterDeviceData(rollerShutterDevice: RollerShutterDevice) {
        launchAsync(
            block = {
                deviceDataRepository.updateDevice(rollerShutterDevice)
            },
            onSuccess = {
                snackLiveEvent.postValue(SnackbarModel(idResMessage = R.string.roller_shutter_device_data_updated))
                navEvent.postValue(NavigationModel(popBack = true))
            })
    }
}