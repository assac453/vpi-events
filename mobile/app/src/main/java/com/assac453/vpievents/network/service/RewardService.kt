package com.assac453.vpievents.network.service

import com.assac453.vpievents.network.model.RewardNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface RewardService {
    @GET("rewards/")
    suspend fun rewards(
        @Query("status") status: String
    ): List<RewardNetwork>
}