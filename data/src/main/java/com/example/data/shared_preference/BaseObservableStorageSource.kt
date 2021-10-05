package com.example.data.shared_preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.example.domain.model.shared_preference.ObservableCacheSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class BaseObservableStorageSource<T>(
    private val dataStore: DataStore<Preferences>,
    private val KEY: Preferences.Key<String>,
    private val mType: Type
) : ObservableCacheSource<T> {

    override suspend fun contains(): Boolean {
        return dataStore.data.map {
            it.contains(KEY)
        }.first()
    }

    override suspend fun remove() {
        dataStore.edit {
            it.remove(KEY)
        }
    }

    override suspend fun put(model: T) {
        dataStore.edit {
            it[KEY] = Gson().toJson(model, mType)
        }
    }

    override suspend fun get(): Flow<T?> {
        return dataStore.data.map {
            it[KEY]?.let { source ->
                Gson().fromJson(source, mType)
            }
        }
    }
}