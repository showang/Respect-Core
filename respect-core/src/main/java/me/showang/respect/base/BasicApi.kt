package me.showang.respect.base

import me.showang.respect.core.ContentType
import me.showang.respect.core.RequestExecutor
import me.showang.respect.core.RespectApi
import java.util.Collections.emptyMap

abstract class BasicApi<ResultType, ChildType : BasicApi<ResultType, ChildType>> : RespectApi {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    fun start(executor: RequestExecutor,
              tag: Any = this,
              failHandler: (Error) -> Unit = {},
              successHandler: (ResultType) -> Unit): ChildType {
        executor.request(this, tag, failHandler) {
            try {
                parse(it).apply {
                    executor.asyncManager.uiThread {
                        successHandler(this)
                    }
                }
            } catch (e: Error) {
                failHandler(e)
            }
        }
        @Suppress("UNCHECKED_CAST")
        return this as? ChildType ?: throw Error("Child type error.")
    }

    @Throws(Exception::class)
    protected abstract fun parse(bytes: ByteArray): ResultType

}