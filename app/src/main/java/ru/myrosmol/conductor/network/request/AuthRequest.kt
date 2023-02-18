package ru.myrosmol.conductor.network.request

import com.google.gson.annotations.SerializedName

data class AuthRequest (
    @SerializedName("mail") val mail: String?,
    @SerializedName("code") val code: Int?
)