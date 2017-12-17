package com.philip.leeswijzer_app

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import philip.com.domain.executor.PostExecutionThread

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}