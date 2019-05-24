package com.meesho.assignment.rx

import io.reactivex.Scheduler

/**
 *  Interface to use different threads during implementation.
 * */
interface SchedulerProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}