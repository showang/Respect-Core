package me.showang.respect

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.showang.respect.core.ApiSpec
import me.showang.respect.core.ContentType
import me.showang.respect.core.RequestExecutor
import me.showang.respect.core.error.ParseException
import me.showang.respect.core.error.RequestException
import java.util.Collections.emptyMap

abstract class RestfulApi<Result> : ApiSpec {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val urlArrayQueries: Map<String, List<String>>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    @Throws(Throwable::class, ParseException::class, RequestException::class)
    open suspend fun request(executor: RequestExecutor): Result = withContext(IO) {
        executor.submit(this@RestfulApi).let {
            runCatching {
                parse(it.readBytes())
            }.run {
                getOrNull()
                    ?: throw exceptionOrNull()?.let(::ParseException)
                        ?: IllegalStateException("No result adn no exception")
            }
        }
    }

    @Throws(Throwable::class)
    protected abstract fun parse(bytes: ByteArray): Result
}