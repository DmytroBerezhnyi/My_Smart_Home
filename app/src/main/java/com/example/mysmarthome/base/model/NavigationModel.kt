package com.example.mysmarthome.base.model

import androidx.navigation.NavDirections
import androidx.navigation.Navigator

data class NavigationModel(
    var direction: NavDirections? = null,
    val popBack: Boolean = false,
    var extras: Navigator.Extras? = null
)