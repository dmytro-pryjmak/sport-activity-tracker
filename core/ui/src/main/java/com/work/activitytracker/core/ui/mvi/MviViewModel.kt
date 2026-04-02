package com.work.activitytracker.core.ui.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

abstract class MviViewModel<S : UiState, A : UiAction, E : UiEffect>(
    defaultState: S,
) : ViewModel() {

    private val actions = Channel<A>(Channel.UNLIMITED)

    val state: StateFlow<S>
        field = MutableStateFlow(defaultState)

    val effects: SharedFlow<Event<E>>
        field = MutableSharedFlow<Event<E>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    init {
        actions.consumeAsFlow()
            .onEach { Log.d(TAG, "Action: $it") }
            .onEach { action -> onAction(action) }
            .launchIn(viewModelScope)
    }

    fun postAction(action: A) {
        actions.trySend(action)
    }

    protected abstract suspend fun onAction(action: A)

    protected fun updateState(reducer: StateHolder<S>.() -> S) =
        state.update { s -> StateHolder(s).reducer() }

    protected fun emitEffect(effect: E) {
        check(effect !is UiEffect.None) { "Cannot emit UiEffect.None" }
        Log.d(TAG, "Effect: $effect")
        effects.tryEmit(Event(effect))
    }

    @JvmInline
    protected value class StateHolder<S>(val state: S)

    companion object {
        private const val TAG = "MVI"
    }
}
