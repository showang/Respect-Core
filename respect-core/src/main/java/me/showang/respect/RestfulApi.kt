package me.showang.respect

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.showang.respect.core.ApiSpec
import me.showang.respect.core.ContentType
import me.showang.respect.core.RequestExecutor
import me.showang.respect.core.error.ParseError
import java.util.Collections.emptyMap

abstract class RestfulApi<Result> : ApiSpec {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    @Throws(Throwable::class, ParseError::class)
    open suspend fun request(executor: RequestExecutor): Result = withContext(IO) {
        executor.submit(this@RestfulApi).let {
            try {
                parse(it.readBytes())
            } catch (e: Throwable) {
                throw ParseError(e)
            }
        }
    }

    @Throws(Throwable::class)
    protected abstract fun parse(bytes: ByteArray): Result

}