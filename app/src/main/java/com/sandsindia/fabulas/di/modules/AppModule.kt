package com.sandsindia.fabulas.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sandsindia.fabulas.utils.SharedPreferenceKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("userPreferences")
    fun providePreferencesDataStoreUser(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {

        return PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(SharedPreferenceKeys.USER_PREFERENCES_FILE)
            }
        )

    }

    @Singleton
    @Provides
    @Named("commonPreferences")
    fun providePreferencesDataStoreCommon(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {

        return PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(SharedPreferenceKeys.COMMON_PREFERENCE_FILE)
            }
        )

    }

}