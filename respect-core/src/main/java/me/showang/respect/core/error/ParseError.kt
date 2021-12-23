package me.showang.respect.core.error

open class ParseError(private val throwable: Throwable) : Error(throwable) {

    override fun toString(): String {
        return "/******* ApiParsingError *******/ (on ${Thread.currentThread()})\n" +
                throwable.stackTrace.map {
                    "[${it.className.substringAfterLast(".")}] ${it.fileName}: ${it.methodName} (line ${it.lineNumber})\n"
                }.reduce { acc, s -> acc + s }
    }

}