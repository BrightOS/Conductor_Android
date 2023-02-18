package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class QuizResponse(
    @SerializedName("question") val question: String?
)