# Tempus

Stay informed and organized with the **Tempus**! This application provides the latest news, categorized by topics, and real-time weather updates to keep you prepared for the day. Save your favorite articles and easily search through categories to find the content that matters most.

---

# Features

- 📰 **Latest News**: Access the most recent news articles from a variety of categories.  
- 🔖 **Save Articles**: Bookmark interesting news to revisit anytime.  
- 🔍 **Search by Categories**: Quickly find news based on your interests.  
- 🌦️ **Weather Updates**: Get real-time weather forecasts for your location.  

# Architecture
- Presentation: Responsible for the UI and input management
- Domain: Contains the business logic, including the use cases and repository interfaces
- Data: Responsible for database operations, network requests and caching.

<img name="Architecture" width="100%" src="./_src/clean_architecture.png"/>

# Technologies Used
|                                                                                                                    |                                                                                            |                                                                                     |
|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------|
| <img src="https://user-images.githubusercontent.com/25181517/185062810-7ee0c3d2-17f2-4a98-9d8a-a9576947692b.png" height="24"> | [**Kotlin**](https://kotlinlang.org/)                                           | Official language for Android development, known for its concise syntax             |
| <img src="https://developer.android.com/static/images/spot-icons/jetpack-compose.svg" height="24">                 | [**Jetpack Compose**](https://developer.android.com/jetpack/compose)                       | A modern toolkit for building native Android UIs                                    |
| <img src="https://upload.wikimedia.org/wikipedia/commons/3/33/Figma-logo.svg" height="24">                         | [**Figma**](https://www.figma.com/)                                                        | A collaborative design tool for creating UI/UX designs                              |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Paging Library**](https://developer.android.com/topic/libraries/architecture/paging)    | Efficiently loads data in chunks (paging)                                           |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**LiveData**](https://developer.android.com/topic/libraries/architecture/livedata)        | An observable data holder for UI updates                                            |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel)      | Retains UI data across configuration changes                                        |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Navigation Components**](https://developer.android.com/guide/navigation/navigation-getting-started) | Simplifies app navigation                                               |
| <img src="https://square.github.io/retrofit/static/icon-square.png" height="24">                                   | [**Retrofit**](https://square.github.io/retrofit/)                                         | A type-safe HTTP client for making API requests                                     |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Room**](https://developer.android.com/training/data-storage/room)                       | A persistence library for local database management                                 |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**DataStore**](https://developer.android.com/topic/libraries/architecture/datastore)      | Async key-value and typed data storage with Kotlin coroutines                       |
| <img src="https://avatars.githubusercontent.com/u/52722434?s=200&v=4" height="24">                                 | [**Coil**](https://coil-kt.github.io/coil/)                                                | An image loading library for Android                                                |
| <img src="https://newsapi.org/images/n-logo-border.png" height="24">                                               | [**NewsAPI**](https://newsapi.org/)                                                        | A third-party API for fetching news articles                                        |
| <img src="https://avatars.githubusercontent.com/u/1743227?s=200&v=4" height="24">                                  | [**OpenWeatherMap**](https://openweathermap.org/)                                          | A third-party API for fetching real-time weather data                               |
| <img src="https://www.iconpacks.net/icons/2/free-injection-icon-3675-thumb.png" height="24">                       | [**Hilt**](https://dagger.dev/hilt/)                                                       | A dependency injection library that simplifies injecting dependencies in Android apps |
| <img src="https://square.github.io/okhttp/assets/images/icon-square.png" height="24">                              | [**OkHttp**](https://square.github.io/okhttp/)                                             | A networking library for HTTP requests                                              |
| <img src="https://junit.org/junit5/assets/img/junit5-logo.png" height="24">                                        | [**JUnit**](https://junit.org/junit5/)                                                     | A testing framework for writing unit tests                                          |
| <img src="https://avatars.githubusercontent.com/u/34787540?s=280&v=4" height="24">                                 | [**MockK**](https://mockk.io/)                                                             | A mocking framework for unit testing Kotlin code                                    |
| <img src="https://avatars.githubusercontent.com/u/49219790?s=48&v=4" height="24">                                  | [**Turbine**](https://github.com/cashapp/turbine)                                          | A testing library for Kotlin Flows                                                  |

# Requirements

- Android 8.0 (API level 26) or higher  
- Stable internet connection  

# Build setup
App is using the following keys to work:

`google-services.json`
- The app will fail if you don't have this.
- Get this file when you create Firebase project with at least one Android application.
- Add the JSON file in `tempus/app/src`

News API and OpenWeatherMap
- Since both of these keys refer to `buildConfig`, create your own API keys and add them in your `local.properties`:
```
API_KEY = <YOUR_NEWS_API_KEY>
WEATHER_KEY = <YOUR_OPEN_WEATHER_MAP_KEY>
```
