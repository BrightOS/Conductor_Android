package ru.myrosmol.conductor.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import ru.myrosmol.conductor.network.request.AuthRequest
import ru.myrosmol.conductor.network.request.ChangeUserContactsRequest
import ru.myrosmol.conductor.network.request.SendQuizzRequest
import ru.myrosmol.conductor.network.response.AuthResponse
import ru.myrosmol.conductor.network.response.DivisionResponse
import ru.myrosmol.conductor.network.response.EventResponse
import ru.myrosmol.conductor.network.response.IsDoneResponse
import ru.myrosmol.conductor.network.response.ProductResponse
import ru.myrosmol.conductor.network.response.ProfileResponse
import ru.myrosmol.conductor.network.response.RoadmapResponse
import ru.myrosmol.conductor.network.response.TaskResponse

interface RestAPI {
    @POST("auth")
    @Headers("Content-Type: application/json")
    fun auth(@Body authRequest: AuthRequest): Call<AuthResponse>

    @GET("auth.send_mail_code")
    fun sendMailCode(@Query("mail") mail: String): Call<IsDoneResponse>

    @GET("me.my_roadmap")
    fun myRoadmap(@Header("token") token: String): Call<RoadmapResponse>

    @GET("me.my_profile")
    fun myProfile(@Header("token") token: String): Call<ProfileResponse>

    @GET("divisions")
    fun getDivisions(@Header("token") token: String): Call<List<DivisionResponse>>

    @GET("me.get_task_by_index")
    fun getTask(
        @Header("token") token: String,
        @Query("task_index") taskId: Int
    ): Call<TaskResponse>

    @POST("send_answers.send_quizz")
    @Headers("Content-Type: application/json")
    fun sendQuizz(
        @Header("token") token: String,
        @Body quizzRequest: SendQuizzRequest
    ): Call<RoadmapResponse>

    @GET("user")
    fun getUsers(
        @Header("token") token: String,
        @Query("division_int_id") divisionId: Int
    ): Call<List<ProfileResponse>>

    @GET("user.by_int_id")
    fun getUser(
        @Header("token") token: String,
        @Query("user_int_id") userId: Int
    ): Call<ProfileResponse>

    @GET("me.get_my_events")
    fun myEvents(@Header("token") token: String): Call<List<EventResponse>>

    @GET("shop")
    fun getAllProducts(@Header("token") token: String): Call<List<ProductResponse>>

    @GET("shop.buy_product")
    fun buyProduct(
        @Header("token") token: String,
        @Query("product_int_id") productId: Int
    ): Call<IsDoneResponse>

    @PATCH("me")
    @Headers("Content-Type: application/json")
    fun changeUserContacts(
        @Header("token") token: String,
        @Body changeUserContactsRequest: ChangeUserContactsRequest
    ): Call<IsDoneResponse>
}