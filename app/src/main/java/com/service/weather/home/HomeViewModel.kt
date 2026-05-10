package com.service.weather.home

import com.service.base_ui.BaseViewModel
import com.service.weather.dependecy.DependencyProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dependencyProvider: DependencyProvider
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    fun getDependencyProvider() = dependencyProvider

    override fun setInitialState(): HomeContract.State = HomeContract.State(
        isLoading = false,
        isError = false
    )

    override fun handleEvents(event: HomeContract.Event) {

    }


}