package com.repleyva.tempus.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.repleyva.tempus.app.di.data.LoggerInterceptors
import com.repleyva.tempus.data.manager.LocalUserManagerImpl
import com.repleyva.tempus.domain.manager.LocalUserManager
import com.repleyva.tempus.domain.use_cases.app_entry.AppEntryUseCases
import com.repleyva.tempus.domain.use_cases.app_entry.ReadAppEntry
import com.repleyva.tempus.domain.use_cases.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

/**
 * Todo: refactor
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @LoggerInterceptors
    fun provideLoggerInterceptor(
        @ApplicationContext context: Context,
    ): Interceptor = ChuckerInterceptor(context)

    @Provides
    @Singleton
    fun provideLocalUserManager(
        dataStore: DataStore<Preferences>,
    ): LocalUserManager = LocalUserManagerImpl(dataStore)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager,
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )
}