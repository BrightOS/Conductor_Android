package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class AttachmentsResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?
)