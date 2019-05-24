package com.meesho.assignment.rx

import com.meesho.assignment.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Implementation of [SchedulerProvider] with actual threads.
 * */
class AppSchedulerProviderProvider : SchedulerProvider {

    override fun mainThread(): io.reactivex.Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): io.reactivex.Scheduler {
        return Schedulers.io()
    }
}