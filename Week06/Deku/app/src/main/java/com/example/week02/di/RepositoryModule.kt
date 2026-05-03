package com.example.week02.di

import com.example.week02.data.repository.ProductRepositoryImpl
import com.example.week02.domain.repository.ProductRepository
import com.example.week02.domain.repository.ProfileRepository
import com.example.week02.data.repository.ProfileRepositoryImpl
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
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl,
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl,
    ): ProfileRepository
}
