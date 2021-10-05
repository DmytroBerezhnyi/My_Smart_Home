package com.example.mysmarthome.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ui.Device
import com.example.domain.model.ui.ProductType
import com.example.mysmarthome.R
import com.example.mysmarthome.base.extension.setOnSingleClickListener
import com.example.mysmarthome.databinding.ItemDeviceBinding

class DeviceAdapter(private val onItemClicked: (Device) -> Unit) :
    ListAdapter<Device, DeviceAdapter.DeviceViewHolder>(DeviceDiff()) {

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            ItemDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class DeviceViewHolder(private val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(device: Device) {
            binding.root.setOnSingleClickListener { onItemClicked.invoke(device) }
            binding.apply {
                tvDeviceTitle.text = device.deviceName

                when (device.productType) {
                    ProductType.Light -> {
                        ivDevice.setImageResource(R.drawable.ic_light)
                        tvDeviceDescription.text = binding.root.context.getString(R.string.light)
                    }
                    ProductType.Heater -> {
                        ivDevice.setImageResource(R.drawable.ic_heater)
                        tvDeviceDescription.text = binding.root.context.getString(R.string.heater)
                    }
                    ProductType.RollerShutter -> {
                        ivDevice.setImageResource(R.drawable.ic_roller_shutter)
                        tvDeviceDescription.text =
                            binding.root.context.getString(R.string.roller_shutter)
                    }
                }
            }
        }
    }
}

private class DeviceDiff : DiffUtil.ItemCallback<Device>() {

    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.id == newItem.id
    }
}