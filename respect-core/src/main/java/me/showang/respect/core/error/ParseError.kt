package me.showang.respect.core.error

class ParseError(private val causeThrowable: Throwable) : Error(causeThrowable) {

    override fun toString(): String {
        return "/******* ApiParsingError *******/ (on ${Thread.currentThread()})\n${causeThrowable.stackTrace.map {
            "[${it.className.substringAfterLast(".")}] ${it.fileName}: ${it.methodName} (line ${it.lineNumber})\n"
        }.reduce { acc, s -> acc + s }}"
    }

}