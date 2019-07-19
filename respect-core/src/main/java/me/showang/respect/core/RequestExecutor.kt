package me.showang.respect.core

import me.showang.respect.core.error.RequestError

interface RequestExecutor {

    @Throws(RequestError::class)
    suspend fun request(api: ApiSpec): ByteArray

    fun cancel(api: ApiSpec)
    fun cancelAll()
}