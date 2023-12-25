package me.showang.respect.core.error

open class ParseException(private val throwable: Throwable) : Exception(throwable) {
    override fun toString(): String {
        return "/******* ApiParsingError *******/ (on ${Thread.currentThread()})\n" +
                throwable.stackTrace.map {
                    "[${it.className.substringAfterLast(".")}] ${it.fileName}: ${it.methodName} (line ${it.lineNumber})\n"
                }.reduce { acc, s -> acc + s }
    }
}
