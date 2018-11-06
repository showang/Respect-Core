package me.showang.respect.core

interface RequestExecutor {

    @Throws(RequestError::class)
    suspend fun request(api: ApiSpec): ByteArray

    fun cancel(api: ApiSpec)
    fun cancelAll()
}