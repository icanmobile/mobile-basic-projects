package com.icanmobile.calllogger.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * Extensions
 */
fun Disposable.disposedBy(bag: CompositeDisposable) {
    bag.add(this)
}