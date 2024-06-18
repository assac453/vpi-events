package com.assac453.vpievents.network.service

import com.assac453.vpievents.network.model.NotificationNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("notifications/")
    suspend fun notifications(): List<NotificationNetwork>

    @GET("notifications/{userId}")
    suspend fun notificationsByUser(
        @Path("userId") id: String
    ): List<NotificationNetwork>
}
