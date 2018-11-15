package me.showang.respect

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import me.showang.respect.core.*
import java.lang.Exception
import java.util.Collections.emptyMap

abstract class RespectApi<Result> : ApiSpec {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    private val apiJob = Job()
    private val apiScope by lazy {
        CoroutineScope(Dispatchers.Main + apiJob)
    }

    fun start(executor: RequestExecutor, scope: CoroutineScope = apiScope,
              failHandler: (Error) -> Unit = {},
              successHandler: (Result) -> Unit) {
        scope.launch {
            try {
                val result = suspend(executor)
                successHandler(result)
            } catch (e: Error) {
                failHandler(e)
            }
        }
    }

    fun cancel() = apiJob.cancel()

    @Throws(Error::class)
    suspend fun suspend(executor: RequestExecutor): Result = suspendParse(executor.request(this))

    @Throws(Throwable::class)
    protected abstract fun parse(bytes: ByteArray): Result

    @Throws(ParseError::class)
    private suspend fun suspendParse(bytes: ByteArray): Result = withContext(IO) {
        try {
            parse(bytes)
        } catch (e: Throwable) {
            throw ParseError(e)
        }
    }
}