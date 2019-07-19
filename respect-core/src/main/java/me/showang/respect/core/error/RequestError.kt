package me.showang.respect.core.error

class RequestError(private val throwable: Throwable? = null, val responseCode: Int, val bodyBytes: ByteArray?) : Error(throwable) {

    override fun toString(): String {
        return "/******* RequestError *******/ (on ${Thread.currentThread()})\n code:$responseCode body: ${bodyBytes?.let { String(it) }}\n message: ${throwable?.message} \n cause: $throwable"
    }

}