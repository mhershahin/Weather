package com.service.forecast_presentation.ui

import com.service.base_ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor() :
    BaseViewModel<ForecastContract.Event, ForecastContract.State, ForecastContract.Effect>() {
    override fun setInitialState(): ForecastContract.State =
        ForecastContract.State(isLoading = false)

    override fun handleEvents(event: ForecastContract.Event) {
        when (event) {

            else -> {

            }
        }
    }
}