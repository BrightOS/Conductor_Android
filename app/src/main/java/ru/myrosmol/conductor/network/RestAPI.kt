package ru.myrosmol.conductor.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import ru.myrosmol.conductor.network.request.AuthRequest
import ru.myrosmol.conductor.network.response.AuthResponse
import ru.myrosmol.conductor.network.response.DivisionResponse
import ru.myrosmol.conductor.network.response.MailCodeResponse
import ru.myrosmol.conductor.network.response.ProfileResponse
import ru.myrosmol.conductor.network.response.RoadmapResponse

interface RestAPI {
    @POST("auth")
    @Headers("Content-Type: application/json")
    fun auth(@Body authRequest: AuthRequest): Call<AuthResponse>

    @GET("auth.send_mail_code")
    fun sendMailCode(@Query("mail") mail: String): Call<MailCodeResponse>

    @GET("me.my_roadmap")
    fun myRoadmap(@Header("token") token: String): Call<RoadmapResponse>

    @GET("me.my_profile")
    fun myProfile(@Header("token") token: String): Call<ProfileResponse>

    @GET("divisions")
    fun getDivisions(@Header("token") token: String): Call<List<DivisionResponse>>
}