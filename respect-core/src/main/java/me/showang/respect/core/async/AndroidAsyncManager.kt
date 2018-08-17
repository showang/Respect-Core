package me.showang.respect.core.async

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AndroidAsyncManager : AsyncManager {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        doAsync {
            val result = background()
            uiThread {
                uiThread(result)
            }
        }
    }

    override fun uiThread(runnable: () -> Unit) {
        uiHandler.post(runnable)
    }

}