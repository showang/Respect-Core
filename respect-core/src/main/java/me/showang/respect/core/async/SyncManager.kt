package me.showang.respect.core.async

class SyncManager : AsyncManager {

    override fun uiThread(runnable: () -> Unit) {
        runnable()
    }

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        uiThread(background())
    }

}