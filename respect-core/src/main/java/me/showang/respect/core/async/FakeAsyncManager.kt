package me.showang.respect.core.async

class FakeAsyncManager : AsyncManager {

    override fun uiThread(runnable: () -> Unit) {
        runnable()
    }

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        uiThread(background())
    }

}