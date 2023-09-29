package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val _state = MutableLiveData<State>(State.Initial)
    val state: LiveData<State> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState !is State.Content || currentState.currencyList.isEmpty()) {
                _state.value = State.Loading
            }
            repository.getCurrencyList().collect {
                _state.value = State.Content(currencyList = it)
            }

        }
    }
}
