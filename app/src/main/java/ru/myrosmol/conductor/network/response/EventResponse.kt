package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class EventResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("desc") val desc: String?,
    @SerializedName("dt") val dateTime: String?,
    @SerializedName("to_user_int_ids") val toUserIds: List<Int>?,
    @SerializedName("division_int_id") val divisionId: Int?
)