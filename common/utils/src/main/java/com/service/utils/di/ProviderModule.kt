package com.service.utils.di

import com.service.utils.dispatcher.DispatcherProvider
import com.service.utils.dispatcher.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}