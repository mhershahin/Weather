package com.service.base_ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


interface ViewEvent

interface ViewState

interface ViewSideEffect

const val SIDE_EFFECTS_KEY = "side-effects_key"

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(COROUTINE_EXCEPTION_HANDLER_MESSAGE, exception.toString())
    }

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    protected fun launchIODispatcher(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO + handler, block = block)
    }

    protected fun launchMainDispatcher(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main + handler, block = block)
    }

    protected fun launchDefaultDispatcher(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Default + handler, block = block)
    }
    
    companion object {
        private const val COROUTINE_EXCEPTION_HANDLER_MESSAGE = "ExceptionHandler"
    }

}