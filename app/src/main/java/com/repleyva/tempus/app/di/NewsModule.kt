package com.repleyva.tempus.app.di

import android.app.Application
import androidx.paging.PagingConfig
import androidx.room.Room
import com.repleyva.tempus.app.di.data.LoggerInterceptors
import com.repleyva.tempus.data.local.NewsDao
import com.repleyva.tempus.data.local.NewsDatabase
import com.repleyva.tempus.data.local.NewsTypeConverter
import com.repleyva.tempus.data.remote.api.NewsApi
import com.repleyva.tempus.data.repository.NewsCachedRepositoryImpl
import com.repleyva.tempus.data.repository.NewsRepositoryImpl
import com.repleyva.tempus.domain.constants.Constants.BASE_URL
import com.repleyva.tempus.domain.constants.Constants.NEWS_DB_NAME
import com.repleyva.tempus.domain.repository.NewsCachedRepository
import com.repleyva.tempus.domain.repository.NewsRepository
import com.repleyva.tempus.domain.use_cases.news.DeleteArticle
import com.repleyva.tempus.domain.use_cases.news.GetArticle
import com.repleyva.tempus.domain.use_cases.news.GetArticles
import com.repleyva.tempus.domain.use_cases.news.GetBreakingNews
import com.repleyva.tempus.domain.use_cases.news.GetCategorizedNews
import com.repleyva.tempus.domain.use_cases.news.GetNewsEverything
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.domain.use_cases.news.SearchNews
import com.repleyva.tempus.domain.use_cases.news.UpsertArticle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(
        @LoggerInterceptors loggerInterceptors: Interceptor,
    ): NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(loggerInterceptors)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = 5,
        initialLoadSize = 20,
        enablePlaceholders = false
    )

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        pagingConfig: PagingConfig,
    ): NewsRepository = NewsRepositoryImpl(newsApi, pagingConfig)

    @Provides
    @Singleton
    fun provideNewsCachedRepository(
        newsDao: NewsDao,
    ): NewsCachedRepository = NewsCachedRepositoryImpl(newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsCachedRepository: NewsCachedRepository,
    ): NewsUseCases = NewsUseCases(
        getBreakingNews = GetBreakingNews(newsRepository),
        getNewsEverything = GetNewsEverything(newsRepository),
        getCategorizedNews = GetCategorizedNews(newsRepository),
        searchNews = SearchNews(newsRepository),
        upsertArticle = UpsertArticle(newsCachedRepository),
        deleteArticle = DeleteArticle(newsCachedRepository),
        getArticles = GetArticles(newsCachedRepository),
        getArticle = GetArticle(newsCachedRepository)
    )

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
    ): NewsDatabase = Room.databaseBuilder(
        context = application,
        klass = NewsDatabase::class.java,
        name = NEWS_DB_NAME
    ).addTypeConverter(NewsTypeConverter())
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase,
    ): NewsDao = newsDatabase.newsDao()
}