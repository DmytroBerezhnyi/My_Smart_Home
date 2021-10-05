package com.example.mysmarthome.base.model

import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar

class SnackbarModel(
    var message: String? = null,
    @StringRes var idResMessage: Int? = null,
    @StringRes var actionTitle: Int? = null,
    val action: (() -> Unit)? = null,
    @BaseTransientBottomBar.Duration val duration: Int = 7000
)