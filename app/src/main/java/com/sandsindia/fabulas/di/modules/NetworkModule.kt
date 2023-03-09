package com.sandsindia.fabulas.di.modules


import com.sandsindia.fabulas.data.remote.FabulasApi
import com.sandsindia.fabulas.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val timeOutDurationInSeconds = 15L

    @Singleton
    @Provides
    fun provideHTTPClient(): OkHttpClient {

        return OkHttpClient
            .Builder()
            .readTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .connectTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideScalarConverterFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    @Singleton
    @Provides
    fun provideMovieApi(
        retrofit: Retrofit
    ): FabulasApi {
        return retrofit.create(FabulasApi::class.java)
    }

}