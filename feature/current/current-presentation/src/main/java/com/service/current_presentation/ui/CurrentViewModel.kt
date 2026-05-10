package com.service.current_presentation.ui

import com.service.base_ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentViewModel @Inject constructor() :
    BaseViewModel<CurrentContract.Event, CurrentContract.State, CurrentContract.Effect>() {
    override fun setInitialState(): CurrentContract.State = CurrentContract.State(isLoading = false)
    override fun handleEvents(event: CurrentContract.Event) {
        when (event) {

            else -> {

            }
        }
    }
}