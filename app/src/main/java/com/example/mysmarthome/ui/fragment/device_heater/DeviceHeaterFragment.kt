package com.example.mysmarthome.ui.fragment.device_heater

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.model.ui.Device
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.databinding.FragmentDeviceHeaterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceHeaterFragment : BaseFragment<FragmentDeviceHeaterBinding, DeviceHeaterViewModel>() {

    private val MIN_TEMPERATURE = 7.0f
    private val MAX_TEMPERATURE = 28.0f
    private val STEP_TEMPERATURE = 0.5f

    private val args: DeviceHeaterFragmentArgs by navArgs()

    override val vm: DeviceHeaterViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_heater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadHeaterDevice(args.heaterDeviceId)
    }

    override fun FragmentDeviceHeaterBinding.initView() {
        tvMaxTemperature.text = getString(R.string.max_temperature, MAX_TEMPERATURE)
        tvMinTemperature.text = getString(R.string.min_temperature, MIN_TEMPERATURE)

        sliderTemperature.apply {
            value = MIN_TEMPERATURE
            stepSize = STEP_TEMPERATURE
            valueFrom = MIN_TEMPERATURE
            valueTo = MAX_TEMPERATURE
            addOnChangeListener { slider, value, fromUser ->
                vm.heaterDevice.value = vm.heaterDevice.value?.copy(
                    temperature = value.toDouble()
                )
            }
        }

        tilDeviceMode.editText?.setOnClickListener {
            vm.heaterDevice.value = vm.heaterDevice.value?.copy(
                mode = when (vm.heaterDevice.value?.mode) {
                    Device.DeviceMode.OFF -> Device.DeviceMode.ON
                    else -> Device.DeviceMode.OFF
                }
            )
        }

        btnUpdate.setOnClickListener {
            vm.heaterDevice.value?.let { heaterDevice ->
                vm.updateHeaterDeviceData(
                    heaterDevice.copy(
                        temperature = sliderTemperature.value.toDouble()
                    )
                )
            }
        }

        ivBack.setOnClickListener {
            onNavigateTo(NavigationModel(popBack = true))
        }
    }

    override fun DeviceHeaterViewModel.setupViewObservers() {
        heaterDevice.observe(viewLifecycleOwner) {
            viewDataBinding.tvCurrentTemperature.text =
                getString(R.string.current_temperature, it.temperature)
            viewDataBinding.sliderTemperature.value = it.temperature.toFloat()
            viewDataBinding.tilDeviceName.editText?.setText(it.deviceName)
            viewDataBinding.tilDeviceMode.editText?.setText(
                when (it.mode) {
                    Device.DeviceMode.ON -> getString(R.string.device_mode_on)
                    Device.DeviceMode.OFF -> getString(R.string.device_mode_off)
                }
            )
        }
    }
}