package com.repleyva.tempus.app.di

import com.repleyva.tempus.data.manager.ArticleCacheManagerImpl
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideArticleCacheManager(): ArticleCacheManager = ArticleCacheManagerImpl()

}
