package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class ProductResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("cost") val cost: Int?,
    @SerializedName("already_bought") val alreadyBought: Boolean?,
)