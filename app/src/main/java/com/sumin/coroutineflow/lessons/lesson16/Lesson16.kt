package com.sumin.coroutineflow.lessons.lesson16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

suspend fun main() {
    loadDataFlow()
        .map { State.Content(it) as State }
        .onStart {
            emit(State.Loading)
        }.retry(2) { // программа будет ждать 1 сек и перезапустит поток, поймав ошибку
            delay(1000)
            true
        }
        .catch {
            emit(State.Error)
        }
        .collect {
            when (it) {
                is State.Content -> println(it.value)
                State.Error -> println("something went wrong")
                State.Loading -> println("loading...")
            }
        }
}

fun loadDataFlow(): Flow<Int> = flow {
    repeat(5) {
        delay(500)
        emit(it)
    }
    throw RuntimeException("Exception from flow block")
}

sealed class State {
    data class Content(val value: Int) : State()
    data object Loading : State()
    data object Error : State()
}