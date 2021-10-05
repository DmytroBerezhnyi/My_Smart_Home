package com.example.mysmarthome.ui.fragment.home

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.domain.extensions.toJson
import com.example.domain.model.ui.Device
import com.example.domain.util.DeviceFilter
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.databinding.HomeFragmentBinding
import com.example.mysmarthome.ui.adapter.DeviceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override val vm: HomeViewModel by viewModels()

    override val layoutId = R.layout.home_fragment

    private val deviceAdapter by lazy {
        DeviceAdapter(onItemClicked = {
            when (it) {
                is Device.LightDevice -> {
                    onNavigateTo(
                        NavigationModel(
                            HomeFragmentDirections.actionHomeFragmentToDeviceLightFragment(it.id)
                        )
                    )
                }
                is Device.HeaterDevice -> {
                    onNavigateTo(
                        NavigationModel(
                            HomeFragmentDirections.actionHomeFragmentToDeviceHeaterFragment(it.id)
                        )
                    )
                }
                is Device.RollerShutterDevice -> {
                    onNavigateTo(
                        NavigationModel(
                            HomeFragmentDirections.actionHomeFragmentToDeviceRollerShutterFragment(it.id)
                        )
                    )
                }
            }
        })
    }

    override fun HomeFragmentBinding.initView() {
        rvDevice.adapter = deviceAdapter
        initSpinner()
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_profile -> {
                    onNavigateTo(
                        NavigationModel(
                            HomeFragmentDirections.actionHomeFragmentToUserProfileFragment()
                        )
                    )
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun HomeFragmentBinding.initSpinner() {
        val deviceTypeList = DeviceFilter.values().map {
            when (it) {
                DeviceFilter.All -> getString(R.string.all)
                DeviceFilter.Light -> getString(R.string.light)
                DeviceFilter.Heater -> getString(R.string.heater)
                DeviceFilter.RollerShutter -> getString(R.string.roller_shutter)
            }
        }
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner_device_filter, deviceTypeList)
        spinnerDeviceType.adapter = spinnerAdapter

        spinnerDeviceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vm.loadDevices(DeviceFilter.values()[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun HomeViewModel.setupViewObservers() {
        deviceList.observe(viewLifecycleOwner) {
            deviceAdapter.submitList(it)
        }
    }
}