package com.example.saywhat.app

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Errors constructor(private val errors: Subject<Throwable>) : Observable<Throwable>(), Observer<Throwable> by errors {
    @Inject
    constructor() : this(PublishSubject.create())

    override fun subscribeActual(observer: Observer<in Throwable>) = errors.subscribe(observer)
}