package me.showang.respect.core.async

interface AsyncManager {

    fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit)

    fun uiThread(runnable: () -> Unit)
}