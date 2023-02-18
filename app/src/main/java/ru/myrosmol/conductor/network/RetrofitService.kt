package ru.myrosmol.conductor.network

import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.myrosmol.conductor.network.request.AuthRequest
import ru.myrosmol.conductor.network.request.SendQuizzRequest
import ru.myrosmol.conductor.network.response.AuthResponse
import ru.myrosmol.conductor.network.response.DivisionResponse
import ru.myrosmol.conductor.network.response.EventResponse
import ru.myrosmol.conductor.network.response.MailCodeResponse
import ru.myrosmol.conductor.network.response.ProfileResponse
import ru.myrosmol.conductor.network.response.RoadmapResponse
import ru.myrosmol.conductor.network.response.TaskResponse

class RetrofitService(val restAPI: RestAPI) {
    fun auth(mail: String, code: Int, onResult: (response: AuthResponse?, code: Int) -> Unit) {
        restAPI.auth(AuthRequest(mail, code)).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun sendMailCode(mail: String, onResult: (response: MailCodeResponse?, code: Int) -> Unit) {
        restAPI.sendMailCode(mail).enqueue(object : Callback<MailCodeResponse> {
            override fun onResponse(
                call: Call<MailCodeResponse>,
                response: Response<MailCodeResponse>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<MailCodeResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun myProfile(token: String, onResult: (response: ProfileResponse?, code: Int) -> Unit) {
        restAPI.myProfile(token).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getDivisions(
        token: String,
        onResult: (response: List<DivisionResponse>?, code: Int) -> Unit
    ) {
        restAPI.getDivisions(token).enqueue(object : Callback<List<DivisionResponse>> {
            override fun onResponse(
                call: Call<List<DivisionResponse>>,
                response: Response<List<DivisionResponse>>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<List<DivisionResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun myRoadmap(token: String, onResult: (response: RoadmapResponse?, code: Int) -> Unit) {
        restAPI.myRoadmap(token).enqueue(object : Callback<RoadmapResponse> {
            override fun onResponse(
                call: Call<RoadmapResponse>,
                response: Response<RoadmapResponse>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<RoadmapResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getTask(token: String, index: Int, onResult: (response: TaskResponse?, code: Int) -> Unit) {
        restAPI.getTask(token, index).enqueue(object : Callback<TaskResponse> {
            override fun onResponse(
                call: Call<TaskResponse>,
                response: Response<TaskResponse>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun sendQuizz(
        token: String,
        taskNum: Int,
        answers: List<String>,
        onResult: (response: RoadmapResponse?, code: Int) -> Unit
    ) {
        restAPI.sendQuizz(token, SendQuizzRequest(taskNum, answers))
            .enqueue(object : Callback<RoadmapResponse> {
                override fun onResponse(
                    call: Call<RoadmapResponse>,
                    response: Response<RoadmapResponse>
                ) {
                    onResult.invoke(response.body(), response.code())
                }

                override fun onFailure(call: Call<RoadmapResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun getUsers(
        token: String,
        divisionId: Int,
        onResult: (response: List<ProfileResponse>?, code: Int) -> Unit
    ) {
        restAPI.getUsers(token, divisionId).enqueue(object : Callback<List<ProfileResponse>> {
            override fun onResponse(
                call: Call<List<ProfileResponse>>,
                response: Response<List<ProfileResponse>>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<List<ProfileResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getUser(
        token: String,
        userId: Int,
        onResult: (response: ProfileResponse?, code: Int) -> Unit
    ) {
        restAPI.getUser(token, userId).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun myEvents(
        token: String,
        onResult: (response: List<EventResponse>?, code: Int) -> Unit
    ) {
        restAPI.myEvents(token).enqueue(object : Callback<List<EventResponse>> {
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>) {
                onResult.invoke(response.body(), response.code())
            }

            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}