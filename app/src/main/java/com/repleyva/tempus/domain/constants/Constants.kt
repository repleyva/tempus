package com.repleyva.tempus.domain.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.repleyva.tempus.BuildConfig
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.model.Source

object Constants {

    const val FIVE_MINUTES_MILLIS = 5 * 60 * 1000L

    const val APP_ENTRY = "app_entry"
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_DB_NAME = "news_db"

    const val WEATHER_KEY = BuildConfig.WEATHER_KEY
    const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"
    const val COUNTRY_PREFIX_DEFAULT = "us"

    val CATEGORY_LIST = arrayListOf(
        "Health",
        "Business",
        "Technology",
        "Entertainment",
        "Science",
        "Sports"
    )

    val SOURCES = listOf(
        "the-verge",
        "vice-news",
        "wired",
        "bbc-news",
        "abc-news",
        "cnn",
        "reuters",
        "al-jazeera-english",
        "the-new-york-times",
        "the-guardian",
        "vox",
        "polygon",
        "ign",
        "cnet",
        "forbes",
        "business-insider",
        "esquire"
    )

    val dummyArticle = Article(
        author = "Author",
        title = "This is title. This is title. This is title. This is title.",
        description = "This is a description. This is a description. This is a description. This is a description. This is a description. This is a description. ",
        content = "This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. This is a content. ",
        publishedAt = "2 hours ago",
        source = Source(id = "", name = "Wired"),
        url = "https://www.wired.com/story/the-worlds-biggest-bitcoin-mine-is-rattling-this-texas-oil-town/",
        urlToImage = "https://media.wired.com/photos/66c5ecc5724cee849e3104da/191:100/w_1280,c_limit/WIRED_BTC_EC_B-Elena-Chudoba.jpg"
    )

    const val APP_SETTINGS = "app_settings"
    val DARK_MODE: Preferences.Key<Boolean> = booleanPreferencesKey("dark_mode")
    val NICKNAME: Preferences.Key<String> = stringPreferencesKey("nickname")
    val SELECTED_EMOJI: Preferences.Key<String> = stringPreferencesKey("selected_emoji")

    val emojis = listOf(
        "😶", "😊", "😎", "🥳", "🤩", "😇", "🤗", "😜",
        "🤔", "😂", "😍", "🤨", "😏", "😬", "🤪", "😴",
        "🙂", "🙃", "😐", "😑", "😏", "😒", "😕", "😌"
    )
}
