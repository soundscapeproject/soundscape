package com.example.dinhh.soundscape.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {

    fun getUIScheduler(): Scheduler

    fun getIOScheduler(): Scheduler
}

class SchedulerProviderImpl: SchedulerProvider {

    val uiScheduler: Scheduler
    val ioScheduler: Scheduler

    init {
        uiScheduler = AndroidSchedulers.mainThread()
        ioScheduler = Schedulers.io()
    }

    override fun getUIScheduler(): Scheduler {
        return uiScheduler
    }

    override fun getIOScheduler(): Scheduler {
        return ioScheduler
    }

}