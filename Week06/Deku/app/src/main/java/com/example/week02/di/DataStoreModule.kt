package com.example.week02.di

import android.content.Context
import com.example.week02.data.local.ProductDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideProductDataStore(
        @ApplicationContext context: Context,
    ): ProductDataStore {
        return ProductDataStore(context)
    }
}
