package com.entain.next.di

import com.entain.next.domain.repository.NextToGoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    @Provides
    @Singleton
    fun provideFakeNextToGoRepository(): NextToGoRepository = FakeNextToGoRepository()
}