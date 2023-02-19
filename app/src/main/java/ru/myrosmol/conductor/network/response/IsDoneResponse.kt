package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class IsDoneResponse(
    @SerializedName("is_done") val isDone: Boolean?
)