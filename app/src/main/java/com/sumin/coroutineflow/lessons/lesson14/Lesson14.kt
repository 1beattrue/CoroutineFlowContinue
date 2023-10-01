package com.sumin.coroutineflow.lessons.lesson14

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val flow = MutableStateFlow(0)

    val producer = scope.launch {
        delay(500)
        repeat(10) {
            println("$it was emitted")
            flow.emit(it)
            delay(200)
        }
    }

    val consumer = scope.launch {
        flow.collectLatest { // отменяет работу, которая уже велась при получении нового emit'а
            delay(5000)
            println("$it was collected")
        }
    }

    producer.join()
    consumer.join()
}