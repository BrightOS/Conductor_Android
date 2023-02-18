package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class QuizResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("question") val question: String?
)