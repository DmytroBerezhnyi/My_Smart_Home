package com.example.mysmarthome.ui.fragment.splash

import androidx.fragment.app.viewModels
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    override val vm: SplashViewModel by viewModels()

    override val layoutId = R.layout.splash_fragment
}