package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class AuthResponse(
    @SerializedName("token") val token: String?
)