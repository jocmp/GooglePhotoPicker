package com.josiahcampbell.googlephotopicker

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

val backgroundPool: CoroutineDispatcher by lazy {
    val numProcessors = Runtime.getRuntime().availableProcessors()
    when {
        numProcessors <= 2 -> newFixedThreadPoolContext(2, "background")
        else -> CommonPool
    }
}