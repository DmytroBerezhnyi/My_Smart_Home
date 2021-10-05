package com.example.data.shared_preference

import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import com.example.domain.model.shared_preference.CacheSource
import java.lang.reflect.Type

class BaseEncryptedStorageSource<T>(
    private val mPreferences: EncryptedSharedPreferences,
    private val KEY: String,
    private val mType: Type,
) : CacheSource<T> {

    override fun contains(): Boolean {
        return mPreferences.contains(KEY)
    }

    override fun remove() {
        mPreferences.edit().remove(KEY).apply()
    }

    override fun put(model: T) {
        val json = Gson().toJson(model, mType)
        mPreferences.edit().putString(KEY, json).apply()
    }

    override fun get(): T {
        return Gson().fromJson(mPreferences.getString(KEY, ""), mType)
    }
}