package me.showang.respect

import me.showang.respect.core.ContentType
import me.showang.respect.core.RequestExecutor
import me.showang.respect.core.ApiSpec
import java.util.Collections.emptyMap
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

abstract class RespectApi<Result, ChildClass : RespectApi<Result, ChildClass>> : ApiSpec {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    open fun start(executor: RequestExecutor,
                   tag: Any = this,
                   failHandler: (Error) -> Unit = {},
                   successHandler: (Result) -> Unit): ChildClass {
        executor.request(this, tag, failHandler) {
            try {
                parse(it).apply {
                    executor.asyncManager.uiThread {
                        successHandler(this)
                    }
                }
            } catch (e: Error) {
                executor.asyncManager.uiThread { failHandler(e) }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return this as? ChildClass ?: throw Error("Child type error.")
    }

    open suspend fun suspend(executor: RequestExecutor): Result = suspendCoroutine { continuation ->
        start(executor, failHandler = {
            continuation.resumeWithException(it)
        }) {
            continuation.resume(it)
        }
    }

    @Throws(Exception::class)
    protected abstract fun parse(bytes: ByteArray): Result

}