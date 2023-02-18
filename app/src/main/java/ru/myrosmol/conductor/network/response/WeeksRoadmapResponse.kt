package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class WeeksRoadmapResponse(
    @SerializedName("weeks") val weeks: List<WeekRoadmapResponse>?
)