package com.example.mysmarthome.ui.fragment.device_roller_shutter

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.model.ui.Device
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.databinding.FragmentDeviceLightBinding
import com.example.mysmarthome.databinding.FragmentDeviceRollerShutterBinding
import com.example.mysmarthome.ui.fragment.device_light.DeviceLightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceRollerShutterFragment :
    BaseFragment<FragmentDeviceRollerShutterBinding, DeviceRollerShutterViewModel>() {

    private val MIN_ROLLER_SHUTTER_POSITION = 0.0f
    private val MAX_ROLLER_SHUTTER_POSITION = 100.0f
    private val STEP_ROLLER_SHUTTER_POSITION = 1.0f

    private val args: DeviceRollerShutterFragmentArgs by navArgs()

    override val vm: DeviceRollerShutterViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_roller_shutter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadRollerShutterDevice(args.rollerShutterDeviceId)
    }

    override fun FragmentDeviceRollerShutterBinding.initView() {
        tvMaxRollerShutterPosition.text =
            getString(R.string.max_roller_shutter_position, MAX_ROLLER_SHUTTER_POSITION.toInt())
        tvMinRollerShutterPosition.text =
            getString(R.string.min_roller_shutter_position, MIN_ROLLER_SHUTTER_POSITION.toInt())

        sliderRollerShutterPosition.apply {
            value = 0.0f
            stepSize = STEP_ROLLER_SHUTTER_POSITION
            valueFrom = MIN_ROLLER_SHUTTER_POSITION
            valueTo = MAX_ROLLER_SHUTTER_POSITION
            addOnChangeListener { slider, value, fromUser ->
                vm.rollerShutterDevice.value = vm.rollerShutterDevice.value?.copy(
                    position = value.toInt()
                )
            }
        }

        btnUpdate.setOnClickListener {
            vm.rollerShutterDevice.value?.let { heaterDevice ->
                vm.updateRollerShutterDeviceData(
                    heaterDevice.copy(
                        position = sliderRollerShutterPosition.value.toInt()
                    )
                )
            }
        }

        ivBack.setOnClickListener {
            onNavigateTo(NavigationModel(popBack = true))
        }
    }

    override fun DeviceRollerShutterViewModel.setupViewObservers() {
        rollerShutterDevice.observe(viewLifecycleOwner) {
            viewDataBinding.sliderRollerShutterPosition.value = it.position.toFloat()
            viewDataBinding.tilDeviceName.editText?.setText(it.deviceName)
            viewDataBinding.tvCurrentRollerShutterPosition.text =
                getString(R.string.current_roller_shutter_position, it.position)
        }
    }
}