package com.repleyva.tempus.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.repleyva.tempus.data.remote.api.NewsApi
import com.repleyva.tempus.domain.model.Article
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val requestType: RequestType,
    private val query: String? = null,
    private val sources: String? = null,
    private val maxArticleCount: Int = Int.MAX_VALUE,
) : PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val pageSize = minOf(params.loadSize, maxArticleCount)

        return try {
            val newsResponse = getApiRequest(page, pageSize)
            if (newsResponse.articles.isEmpty()) {
                println("No articles found for $requestType with query: $query")
            }
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles
                .distinctBy { it.title }
                .take(maxArticleCount)

            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: SocketTimeoutException) {
            LoadResult.Error(Throwable("Network timeout. Please try again."))
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 429) {
                val retryAfter = e.response()?.headers()?.get("Retry-After")?.toIntOrNull() ?: 60
                delay(retryAfter * 1000L)
            }
            LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    private suspend fun getApiRequest(
        page: Int,
        pageSize: Int,
    ) = when (requestType) {
        RequestType.CategorizedNews -> newsApi.getCategorizedNews(
            category = query.orEmpty(),
            page = page,
            pageSize = pageSize * 2
        )
        RequestType.SearchNews -> newsApi.searchNews(
            searchQuery = query.orEmpty(),
            sources = sources.orEmpty(),
            page = page,
            pageSize = pageSize * 2
        )
        RequestType.NewsEverything -> newsApi.getNewsEverything(
            sources = sources.orEmpty(),
            page = page,
            pageSize = pageSize * 2
        )
        RequestType.BreakingNews -> newsApi.getBreakingNews(
            country = query ?: "co",
            page = page,
            pageSize = pageSize * 2
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

sealed class RequestType {
    data object CategorizedNews : RequestType()
    data object SearchNews : RequestType()
    data object NewsEverything : RequestType()
    data object BreakingNews : RequestType()
}