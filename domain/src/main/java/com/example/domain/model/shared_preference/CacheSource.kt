package com.example.domain.model.shared_preference

interface CacheSource<T> {
    fun contains(): Boolean

    fun remove()

    fun put(model: T)

    fun get(): T
}

abstract class BaseCache<T>(
    protected var sp: CacheSource<T>
) {

    val isCached: Boolean
        get() = sp.contains()


    fun remove() {
        sp.remove()
    }

    open fun put(model: T): T {
        sp.put(model)
        return model
    }

    open fun get(): T {
        return sp.get()
    }
}