package com.assac453.vpievents.network.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterOnEventService {
    @POST("events/register")
    suspend fun registerEvent(@Body requestBody: EventRegistrationRequest): Response<RegistrationResponse>
}

data class EventRegistrationRequest(
    val user_id: String,
    val event_id: String,
    val latitude: Double,
    val longitude: Double
)

data class RegistrationResponse(val success: String?, val error: String?)

data class ErrorResponse(val error: String)