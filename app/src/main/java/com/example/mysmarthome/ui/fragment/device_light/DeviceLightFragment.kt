package com.example.mysmarthome.ui.fragment.device_light

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.model.ui.Device.DeviceMode
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.databinding.FragmentDeviceLightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceLightFragment : BaseFragment<FragmentDeviceLightBinding, DeviceLightViewModel>() {

    private val MIN_LIGHT_INTENSITY = 0.0f
    private val MAX_LIGHT_INTENSITY = 100.0f
    private val STEP_LIGHT_INTENSITY = 1.0f

    private val args: DeviceLightFragmentArgs by navArgs()

    override val vm: DeviceLightViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_light

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadLightDevice(args.lightDeviceId)
    }

    override fun FragmentDeviceLightBinding.initView() {
        tvMaxIntensity.text = getString(R.string.max_light_intensity, MAX_LIGHT_INTENSITY.toInt())
        tvMinIntensity.text = getString(R.string.min_light_intensity, MIN_LIGHT_INTENSITY.toInt())

        sliderLightIntensity.apply {
            value = 0.0f
            stepSize = STEP_LIGHT_INTENSITY
            valueFrom = MIN_LIGHT_INTENSITY
            valueTo = MAX_LIGHT_INTENSITY
            addOnChangeListener { slider, value, fromUser ->
                vm.lightDevice.value = vm.lightDevice.value?.copy(
                    intensity = value.toInt()
                )
            }
        }

        tilDeviceMode.editText?.setOnClickListener {
            vm.lightDevice.value = vm.lightDevice.value?.copy(
                mode = when (vm.lightDevice.value?.mode) {
                    DeviceMode.OFF -> DeviceMode.ON
                    else -> DeviceMode.OFF
                }
            )
        }

        btnUpdate.setOnClickListener {
            vm.lightDevice.value?.let { heaterDevice ->
                vm.updateLightDeviceData(
                    heaterDevice.copy(
                        intensity = sliderLightIntensity.value.toInt()
                    )
                )
            }
        }

        ivBack.setOnClickListener {
            onNavigateTo(NavigationModel(popBack = true))
        }
    }

    override fun DeviceLightViewModel.setupViewObservers() {
        lightDevice.observe(viewLifecycleOwner) {
            viewDataBinding.tvCurrentLightIntensity.text =
                getString(R.string.current_light_intensity, it.intensity)
            viewDataBinding.sliderLightIntensity.value = it.intensity.toFloat()
            viewDataBinding.tilDeviceName.editText?.setText(it.deviceName)
            viewDataBinding.tilDeviceMode.editText?.setText(
                when (it.mode) {
                    DeviceMode.ON -> getString(R.string.device_mode_on)
                    DeviceMode.OFF -> getString(R.string.device_mode_off)
                }
            )
        }
    }
}