package me.showang.respect.core

interface RequestExecutor {
    suspend fun request(api: ApiSpec): ByteArray
    fun cancel(api: ApiSpec)
    fun cancelAll()
}