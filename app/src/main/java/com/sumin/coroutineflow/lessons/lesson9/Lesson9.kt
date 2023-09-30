package com.sumin.coroutineflow.lessons.lesson9

import com.sumin.coroutineflow.lessons.lesson8.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

suspend fun main() {
    val flow = MutableSharedFlow<Int>()

    coroutineScope.launch {
        repeat(100) {
            flow.emit(it)
            delay(1000)
        }
    }

//    val job1 = coroutineScope.launch {
//        flow.collect {
//            println(it)
//        }
//    }
    delay(5000)
    val job2 = coroutineScope.launch {
        flow.collect {
            println(it)
        }
    }
//    job1.join()
    job2.join()
}

