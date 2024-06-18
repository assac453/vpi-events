package com.assac453.vpievents.network.service

import com.assac453.vpievents.network.model.EventNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {
    @GET("events/")
    suspend fun events(
        @Query("event_type") eventType: String
    ): List<EventNetwork>

}