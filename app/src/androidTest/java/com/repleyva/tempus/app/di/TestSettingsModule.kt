package com.repleyva.tempus.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.repleyva.tempus.data.repository.SettingsRepositoryImpl
import com.repleyva.tempus.domain.constants.Constants
import com.repleyva.tempus.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.io.File
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SettingsModule::class]
)
@Module
object TestSettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDatastore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { File.createTempFile("test" + Constants.APP_SETTINGS, ".preferences_pb") }
    )

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>,
    ): SettingsRepository = SettingsRepositoryImpl(dataStore)
}