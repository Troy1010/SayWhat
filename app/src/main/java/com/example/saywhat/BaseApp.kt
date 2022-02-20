package com.example.saywhat

import android.app.Application
import io.reactivex.rxjava3.plugins.RxJavaPlugins

open class BaseApp : Application() {
    override fun onCreate() {
        logz("!*!*! START")
        super.onCreate()

        // # Configure Rx
        RxJavaPlugins.setErrorHandler { throw it.cause ?: it }
    }
}