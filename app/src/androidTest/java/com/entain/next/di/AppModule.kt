package com.entain.next.di

import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.entain.next.domain.usecase.NextToGoRacing
import com.entain.next.domain.util.Resource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun fakeFakeNextToGoDataSource(): FakeNextToGoDataSource = FakeNextToGoDataSource()

    @Provides
    @Singleton
    fun provideNextToGoRepository(fakeNextToGoDataSource: FakeNextToGoDataSource): NextToGoRepository =
        object : NextToGoRepository {
            override suspend fun getNextToGoRacingSummery(): Flow<Resource<List<NextToGo>>> {
                return fakeNextToGoDataSource.getFakeData()
            }

            override suspend fun clearLocalCache() {
                fakeNextToGoDataSource.clearLocalCache()
            }

            override suspend fun deleteExpiredEvent(nextToGo: NextToGo?) {
                fakeNextToGoDataSource.deleteExpiredEvent(nextToGo)
            }

            override suspend fun deleteExpiredEvents() {
                fakeNextToGoDataSource.deleteExpiredEvents()
            }

        }

    @Provides
    @Singleton
    fun nextToGoRacing(repository: NextToGoRepository) = NextToGoRacing(repository)
}