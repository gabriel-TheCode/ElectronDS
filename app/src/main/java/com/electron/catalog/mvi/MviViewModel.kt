package com.electron.catalog.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Minimal MVI store shared by the app's screens.
 *
 * - **Model**: one immutable [state] per screen, exposed as a StateFlow.
 * - **View**: stateless composables render the state and never mutate it.
 * - **Intent**: every user action is sent to [onIntent] as a value.
 *
 * [reduce] is a pure function `(state, intent) -> state`, so every change on
 * screen traces back to one intent, and reducers are unit-tested without
 * Android. The screens of this app have no asynchronous work, so there is
 * no side-effect channel: navigation is state too (see AppState).
 */
abstract class MviViewModel<S : Any, I : Any>(initialState: S) : ViewModel() {

    private val mutableState = MutableStateFlow(initialState)

    val state: StateFlow<S> = mutableState.asStateFlow()

    fun onIntent(intent: I) {
        mutableState.update { reduce(it, intent) }
    }

    protected abstract fun reduce(state: S, intent: I): S
}
