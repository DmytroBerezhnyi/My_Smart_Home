package com.example.mysmarthome.ui.fragment.splash

import com.example.mysmarthome.base.architecture.BaseViewModel
import com.example.mysmarthome.base.model.NavigationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val SPLASH_TIME = 3000L

    init {
        launchAsync(block = {
            delay(SPLASH_TIME)
            navEvent.value =
                NavigationModel(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        })
    }
}