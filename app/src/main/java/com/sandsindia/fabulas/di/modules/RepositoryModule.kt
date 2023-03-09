package com.sandsindia.fabulas.di.modules


import com.sandsindia.fabulas.data.remote.FabulasApi
import com.sandsindia.fabulas.data.repository.FabulasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        fabulasApi: FabulasApi
    ): FabulasRepository {

        return FabulasRepository(fabulasApi = fabulasApi)

    }

}