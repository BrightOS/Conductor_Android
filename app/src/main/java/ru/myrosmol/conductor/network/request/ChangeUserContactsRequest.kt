package ru.myrosmol.conductor.network.request

import com.google.gson.annotations.SerializedName

class ChangeUserContactsRequest(
    @SerializedName("telegram") val telegram: String?,
    @SerializedName("whatsapp") val whatsapp: String?,
    @SerializedName("vk") val vk: String?,
)