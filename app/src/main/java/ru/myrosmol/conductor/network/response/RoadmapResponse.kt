package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class RoadmapResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("created_by_int_id") val createdByIntId: String?,
    @SerializedName("easy_view2") val weeksRoadmap: WeeksRoadmapResponse?
)