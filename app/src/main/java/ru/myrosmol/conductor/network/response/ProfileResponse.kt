package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class ProfileResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("fullname") val fullname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("tokens") val tokens: List<String>?,
    @SerializedName("role") val role: String?,
    @SerializedName("coins") val coins: Int?,
    @SerializedName("position") val position: String?,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("telegram") val telegram: String?,
    @SerializedName("whatsapp") val whatsapp: String?,
    @SerializedName("vk") val vk: String?,
    @SerializedName("roadmap_int_id") val roadmapId: Int?,
    @SerializedName("division_int_id") val divisionId: Int?
)