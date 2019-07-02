package me.showang.respect.core

interface ApiSpec {

    companion object {
        const val DEFAULT_TIMEOUT = 3000L
    }

    val url: String
    val httpMethod: HttpMethod
    val contentType: String

    val headers: Map<String, String>
    val urlQueries: Map<String, String>

    val body: ByteArray

    val priority: Priority get() = Priority.NORMAL
    val timeout: Long get() = DEFAULT_TIMEOUT

}