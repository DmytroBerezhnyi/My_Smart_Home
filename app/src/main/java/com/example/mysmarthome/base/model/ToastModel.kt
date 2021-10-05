package com.example.mysmarthome.base.model

import androidx.annotation.StringRes

data class ToastModel(
    var message: String? = null,
    @StringRes var idResMessage: Int? = null
)
