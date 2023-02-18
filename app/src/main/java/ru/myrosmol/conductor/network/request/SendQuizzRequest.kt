package ru.myrosmol.conductor.network.request

import com.google.gson.annotations.SerializedName

class SendQuizzRequest(
    @SerializedName("task_num") val taskNum: Int?,
    @SerializedName("answers") val answers: List<String>?
)