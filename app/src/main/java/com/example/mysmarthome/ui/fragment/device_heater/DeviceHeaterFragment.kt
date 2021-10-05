package com.example.mysmarthome.ui.fragment.device_heater

import androidx.fragment.app.viewModels
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.databinding.FragmentDeviceHeaterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceHeaterFragment : BaseFragment<FragmentDeviceHeaterBinding, DeviceHeaterViewModel>() {

    override val vm: DeviceHeaterViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_heater

}