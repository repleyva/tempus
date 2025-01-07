package com.repleyva.tempus.domain.use_cases.news

data class NewsUseCases(
    val getBreakingNews: GetBreakingNews,
    val getNewsEverything: GetNewsEverything,
    val getCategorizedNews: GetCategorizedNews,
    val searchNews: SearchNews,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val getArticles: GetArticles,
    val getArticle: GetArticle,
)