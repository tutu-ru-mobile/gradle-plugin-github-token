package view

import lib.MviComponent
import model.AppState
import model.Intent
import model.store

class ApplicationComponent : MviComponent<AppState, Intent>(
    store,
    { state: AppState, userIntent: (Intent) -> Unit ->
        renderAppState(state, userIntent)
    }
)
