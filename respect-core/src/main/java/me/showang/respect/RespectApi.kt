package me.showang.respect

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import me.showang.respect.core.ApiSpec
import me.showang.respect.core.ContentType
import me.showang.respect.core.RequestExecutor
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
                successHandler(suspend(executor))
            } catch (e: Error) {
                failHandler(e)
            }
        }
    }

    fun cancel() = apiJob.cancel()

    suspend fun suspend(executor: RequestExecutor): Result = suspendParse(executor.request(this))

    @Throws(Error::class)
    protected abstract fun parse(bytes: ByteArray): Result

    @Throws(Error::class)
    private suspend fun suspendParse(bytes: ByteArray): Result = withContext(IO) {
        parse(bytes)
    }
}