package com.example.mysmarthome.ui.fragment.device_roller_shutter

import androidx.fragment.app.viewModels
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.databinding.FragmentDeviceRollerShutterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceRollerShutterFragment :
    BaseFragment<FragmentDeviceRollerShutterBinding, DeviceRollerShutterViewModel>() {

    override val vm: DeviceRollerShutterViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_roller_shutter
}