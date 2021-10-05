package com.example.domain.model.shared_preference

import kotlinx.coroutines.flow.Flow

interface ObservableCacheSource<T> {

    suspend fun contains(): Boolean

    suspend fun remove()

    suspend fun put(model: T)

    suspend fun get(): Flow<T?>
}

abstract class BaseObservableCache<T>(
    private val cacheSource: ObservableCacheSource<T>,
) {

    suspend fun isCached(): Boolean {
        return cacheSource.contains()
    }

    open suspend fun put(model: T): T {
        cacheSource.put(model)
        return model
    }

    open suspend fun remove() {
        cacheSource.remove()
    }

    open suspend fun get(): Flow<T?> {
        return cacheSource.get()
    }
}