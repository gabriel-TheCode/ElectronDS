package com.electron.catalog.demo

import com.electron.catalog.mvi.MviViewModel

class DemoViewModel : MviViewModel<DemoState, DemoIntent>(DemoState()) {
    override fun reduce(state: DemoState, intent: DemoIntent): DemoState = state.reduce(intent)
}
