package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class DayRoadmapResponse(
    @SerializedName("day") val day: Int?,
    @SerializedName("tasks") val tasks: List<TaskResponse>?,
)