package com.electron.catalog.app

import com.electron.catalog.mvi.MviViewModel

class AppViewModel : MviViewModel<AppState, AppIntent>(AppState()) {
    override fun reduce(state: AppState, intent: AppIntent): AppState = state.reduce(intent)
}
