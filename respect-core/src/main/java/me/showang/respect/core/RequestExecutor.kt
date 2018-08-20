package me.showang.respect.core

import me.showang.respect.core.async.AsyncManager

interface RequestExecutor {

    fun request(api: ApiSpec,
                tag: Any = api,
                failCallback: (error: Error) -> Unit = {},
                completeCallback: (response: ByteArray) -> Unit)

    fun cancel(tag: Any)
    fun cancelAll()

    val asyncManager: AsyncManager
}