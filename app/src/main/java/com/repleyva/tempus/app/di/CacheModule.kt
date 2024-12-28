package com.repleyva.tempus.app.di

import android.content.Context
import com.repleyva.tempus.data.manager.ArticleCacheManagerImpl
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Todo: refactor
 */

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideArticleCacheManager(@ApplicationContext context: Context): ArticleCacheManager {
        return ArticleCacheManagerImpl(context)
    }
}
