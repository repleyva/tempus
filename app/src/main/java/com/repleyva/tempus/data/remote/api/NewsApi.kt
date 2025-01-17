package com.repleyva.tempus.data.remote.api

import com.repleyva.tempus.data.remote.dto.NewsResponse
import com.repleyva.tempus.domain.constants.Constants.API_KEY
import com.repleyva.tempus.domain.constants.Constants.COUNTRY_PREFIX_DEFAULT
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNewsEverything(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getCategorizedNews(
        @Query("category") category: String,
        @Query("country") country: String = COUNTRY_PREFIX_DEFAULT,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse
}