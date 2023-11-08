package com.entain.next.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.entain.next.data.local.NextToGoDb
import com.entain.next.data.remote.EntainApi
import com.entain.next.data.repository.NextToGoRepositoryImpl
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.repository.NextToGoRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEntainApi(): EntainApi =
        Retrofit.Builder().baseUrl(EntainApi.URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            ).client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            ).build()
            .create(EntainApi::class.java)

    @Provides
    @Singleton
    fun provideNextToGoDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NextToGoDb::class.java, "nexttogoentity").build()

    @Provides
    @Singleton
    fun provideNextToGoRepository(
        entainApi: EntainApi,
        nextToGoDb: NextToGoDb
    ): NextToGoRepository =
        NextToGoRepositoryImpl(entainApi, nextToGoDb)


}