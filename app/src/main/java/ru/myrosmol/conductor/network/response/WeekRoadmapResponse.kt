package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class WeekRoadmapResponse(
    @SerializedName("week") val week: Int?,
    @SerializedName("days") val days: List<DayRoadmapResponse>?,
)