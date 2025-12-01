package com.example.levelupgamermovil.model

import com.google.gson.annotations.SerializedName

data class GameNews(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("short_description") val description: String
)