package com.sumin.coroutineflow.lessons.lesson13

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        val flow = flow {
            repeat(10) {
                println("$it was emitted")
                emit(it)
                delay(200)
            }
        }.buffer(1, BufferOverflow.SUSPEND) // хороший пример с ребенком, тарелкой и печеньями
//        }.buffer(1, BufferOverflow.DROP_LATEST) // игнорирует новые элементы, если буфер переполнен
//        }.buffer(1, BufferOverflow.DROP_OLDEST) // выкидывает самый старый элемент
        flow.collect {
            println("$it was collected")
            delay(1000)
        }
    }

    job.join()
}