package com.example.week02.di

import com.example.week02.data.local.ProductLocalDataSource
import com.example.week02.data.local.ProductLocalDataSourceImpl
import com.example.week02.data.repository.ProductLocalRepositoryImpl
import com.example.week02.data.repository.ReqresRemoteRepositoryImpl
import com.example.week02.domain.repository.ProductLocalRepository
import com.example.week02.domain.repository.ReqresRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductLocalDataSource(
        impl: ProductLocalDataSourceImpl,
    ): ProductLocalDataSource

    @Binds
    @Singleton
    abstract fun bindProductLocalRepository(
        impl: ProductLocalRepositoryImpl,
    ): ProductLocalRepository

    @Binds
    @Singleton
    abstract fun bindReqresRemoteRepository(
        impl: ReqresRemoteRepositoryImpl,
    ): ReqresRemoteRepository
}
