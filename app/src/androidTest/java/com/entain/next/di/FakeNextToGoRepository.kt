package com.entain.next.di

import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.util.Resource
import com.entain.next.util.SECONDS
import com.entain.next.util.currentTimeToSeconds
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FakeNextToGoDataSource @Inject constructor() {
    private val flow = MutableStateFlow<Resource<List<NextToGo>>>(Resource.Loading())

    fun getFakeData() = flow

    fun clearLocalCache() {
        flow.tryEmit(Resource.Success(data = listOf()))
    }

    fun deleteExpiredEvent(nextToGo: NextToGo?) {
        val items = flow.value.data?.toMutableList()
        items?.remove(nextToGo)
        items?.removeAll(items.filter { (it.adStartTimeInSeconds - currentTimeToSeconds()) <= -SECONDS })
        flow.tryEmit(Resource.Success(data = items))
    }

    suspend fun emit(resource: Resource<List<NextToGo>>) {
        flow.emit(resource)
    }
}