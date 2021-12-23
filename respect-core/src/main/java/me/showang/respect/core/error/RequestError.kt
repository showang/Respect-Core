package me.showang.respect.core.error

class RequestError(
    private val throwable: Throwable? = null,
    val responseCode: Int = -1,
    val bodyBytes: ByteArray? = null
) : Error(throwable) {

    override fun toString(): String {
        return "/******* RequestError *******/ (on ${Thread.currentThread()})\n" +
                "code:$responseCode message: ${throwable?.message}\n" +
                "body: ${bodyBytes?.let { String(it) }}\n" +
                "cause: $throwable"
    }

}