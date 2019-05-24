package com.meesho.assignment.rx.extensions

import com.meesho.assignment.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Completable]
 * */
fun Completable.doOnBackgroundObserveOnMain(schedulerProvider: SchedulerProvider): Completable {
    return this.subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.mainThread())
}


/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a [Single]
 * */
fun <T> Single<T>.doOnBackgroundObserveOnMain(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.mainThread())
}


/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Observable]
 * */
fun <T> Observable<T>.doOnBackgroundObserveOnMain(schedulerProvider: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.mainThread())
}

/**
 * Extension function to add a Disposable to a CompositeDisposable
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Extension function to subscribe on the background thread for a Observable
 * */
fun <T> Observable<T>.doOnBackground(schedulerProvider: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProvider.io())
}