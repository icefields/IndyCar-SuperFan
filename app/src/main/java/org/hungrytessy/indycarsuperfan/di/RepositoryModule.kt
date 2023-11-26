package org.hungrytessy.indycarsuperfan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.hungrytessy.indycarsuperfan.data.IndyRepositoryImpl
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIndyRepository(
        stockRepositoryImpl: IndyRepositoryImpl
    ): IndyRepository
}
