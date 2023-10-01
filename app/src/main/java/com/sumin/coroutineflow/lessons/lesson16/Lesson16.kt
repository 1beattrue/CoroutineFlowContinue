package com.sumin.coroutineflow.lessons.lesson16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun main() {
    val flow = loadDataFlow()

    flow.collect {
        throw RuntimeException("Exception from collect")
    }
}

fun loadDataFlow(): Flow<Int> = flow {
    try {
        repeat(5) {
            delay(500)
            emit(it)
        }
    } catch (e: Exception) {
        println(e) // ловит исключение из блока collect, потому что на низком уровне collect работает именно с emit
    }
}