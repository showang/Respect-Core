package me.showang.respect.core

class RequestError(throwable: Throwable, val responseCode: Int, val bodyBytes: ByteArray?) : Error(throwable)