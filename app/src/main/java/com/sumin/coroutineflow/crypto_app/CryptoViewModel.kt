package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val loadingFlow = MutableSharedFlow<State>()

    var a = Dispatchers.Unconfined

    val state: Flow<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart { emit(State.Loading) }
        .mergeWith(loadingFlow)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed() // SharingStarted.Eagerly - emit сразу, SharingStarted.Lazily - emit при первой подписке
        )

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
        return merge(this, another)
    }

    fun refreshList() {
        viewModelScope.launch {
            loadingFlow.emit(State.Loading)
            repository.refreshList()
        }
    }
}
