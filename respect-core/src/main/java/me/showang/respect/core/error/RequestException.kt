package me.showang.respect.core.error

@Suppress("MemberVisibilityCanBePrivate")
class RequestException(
    throwable: Throwable? = null,
    val responseCode: Int = -1,
    val bodyBytes: ByteArray? = null
) : Exception(throwable) {
    override fun toString(): String {
        return "/******* RequestError *******/ (on ${Thread.currentThread()})\n" +
                "code:$responseCode message: ${cause?.message}\n" +
                "body: ${bodyBytes?.let { String(it) }}\n" +
                "cause: $cause"
    }
}
