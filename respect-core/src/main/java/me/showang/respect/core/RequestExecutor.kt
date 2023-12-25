package me.showang.respect.core

import me.showang.respect.core.error.RequestException
import java.io.InputStream

interface RequestExecutor {

    @Throws(RequestException::class)
    suspend fun submit(api: ApiSpec): InputStream

    fun cancel(api: ApiSpec)
    fun cancelAll()
}