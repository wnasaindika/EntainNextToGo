package com.entain.next.di

import com.entain.next.data.local.NextToGoDao
import com.entain.next.data.local.NextToGoDb
import com.entain.next.data.remote.EntainApi
import com.entain.next.data.repository.NextToGoRepositoryImpl
import com.entain.next.domain.repository.NextToGoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNextToGoRepository(
        entainApi: EntainApi,
        localDb: NextToGoDb
    ): NextToGoRepository =
        NextToGoRepositoryImpl(entainApi, localDb)
}