package ru.myrosmol.conductor.network.response

import com.google.gson.annotations.SerializedName

class TaskResponse(
    @SerializedName("int_id") val id: Int?,
    @SerializedName("created") val created: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("is_confirmed_by_hr_int_id") val confirmedHrIntId: Int?,
    @SerializedName("coins") val coins: Int?,
    @SerializedName("is_completed") val completed: Boolean?,
    @SerializedName("week_num") val weekNumber: Int?,
    @SerializedName("day_num") val dayNumber: Int?,
    @SerializedName("attachments") val attachments: List<AttachmentsResponse>?,
    @SerializedName("quizzes") val quizzes: List<QuizResponse>?,
)