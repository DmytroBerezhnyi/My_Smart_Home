package com.example.mysmarthome.base.extension

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

inline fun <reified M : Parcelable> AppCompatActivity.getParcelableData(key: String) =
    intent?.getParcelableExtra<M>(key)

inline fun <reified A : AppCompatActivity> A.getBoolean(key: String) =
    intent?.getBooleanExtra(key, false)