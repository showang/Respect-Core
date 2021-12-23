package me.showang.respect.core

import me.showang.respect.core.error.RequestError
import java.io.InputStream

interface RequestExecutor {

    @Throws(RequestError::class)
    suspend fun submit(api: ApiSpec): InputStream

    fun cancel(api: ApiSpec)
    fun cancelAll()
}