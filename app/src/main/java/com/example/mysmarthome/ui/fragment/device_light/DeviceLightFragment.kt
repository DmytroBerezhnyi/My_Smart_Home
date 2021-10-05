package com.example.mysmarthome.ui.fragment.device_light

import androidx.fragment.app.viewModels
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.databinding.FragmentDeviceLightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceLightFragment : BaseFragment<FragmentDeviceLightBinding, DeviceLightViewModel>() {

    override val vm: DeviceLightViewModel by viewModels()

    override val layoutId = R.layout.fragment_device_light

}