package com.hexan.ripper.network

import io.reactivex.subjects.PublishSubject


/**
 * Created by James Cooper on 12/04/2018.
 */
object RxNetworkErrorBus {

    private val bus = PublishSubject.create<Int>()

    fun send(o: Int) {
        bus.onNext(o)
    }

    fun toObservable(): PublishSubject<Int> {
        return bus
    }
}