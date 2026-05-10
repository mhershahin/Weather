package com.service.rader_presentation.ui

import com.service.base_ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RadarViewModel @Inject constructor() :
    BaseViewModel<RadarContract.Event, RadarContract.State, RadarContract.Effect>() {
    override fun setInitialState(): RadarContract.State = RadarContract.State(isLoading = false)
    override fun handleEvents(event: RadarContract.Event) {
        when (event) {

            else -> {

            }
        }
    }
}