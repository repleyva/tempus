package com.repleyva.tempus.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Article(
    val author: String?,
    val content: String? = null,
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source,
    val title: String? = "",
    @PrimaryKey val url: String,
    val urlToImage: String? = "",
) : Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String,
) : Parcelable