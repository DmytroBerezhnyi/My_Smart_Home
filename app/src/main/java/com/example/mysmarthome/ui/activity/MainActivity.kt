package com.example.mysmarthome.ui.activity

import androidx.activity.viewModels
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseActivity
import com.example.mysmarthome.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {

    override val vm: MainVM by viewModels()

    override val layoutId: Int = R.layout.activity_main
}