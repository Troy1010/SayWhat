package com.example.saywhat.all.extensions

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

fun MutableSharedFlow<Unit>.easyEmit() = easyEmit(Unit)
fun <T> MutableSharedFlow<T>.easyEmit(x: T) = runBlocking { emit(x) }