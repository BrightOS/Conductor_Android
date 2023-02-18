package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class DivisionResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("title") val title: String?
)