package com.assac453.vpievents.data

import com.assac453.vpievents.data.repository.EventRepository
import com.assac453.vpievents.data.repository.NetworkEventRepository
import com.assac453.vpievents.data.repository.NetworkNotificationRepository
import com.assac453.vpievents.data.repository.NetworkRewardRepository
import com.assac453.vpievents.data.repository.NotificationRepository
import com.assac453.vpievents.data.repository.RewardRepository
import com.assac453.vpievents.logic.Constants
import com.assac453.vpievents.network.service.EventService
import com.assac453.vpievents.network.service.NotificationService
import com.assac453.vpievents.network.service.RegisterOnEventService
import com.assac453.vpievents.network.service.RewardService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val eventRepository: EventRepository
    val rewardRepository: RewardRepository
    val notificationRepository: NotificationRepository
}

class DefaultAppContainer : AppContainer{
    private val BASE_URL = "http://${Constants.SERVER_ADDRESS}:${Constants.PORT}/api/v1/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitServiceEvents: EventService by lazy {
        retrofit.create(EventService::class.java)
    }

    private val retrofitServiceRewards: RewardService by lazy {
        retrofit.create(RewardService::class.java)
    }

    private val retrofitServiceNotifications: NotificationService by lazy {
        retrofit.create(NotificationService::class.java)
    }

    public val retrofitServiceRegisterOnEventService: RegisterOnEventService by lazy {
        retrofit.create(RegisterOnEventService::class.java)
    }

    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(retrofitServiceEvents)
    }
    override val rewardRepository: RewardRepository by lazy {
        NetworkRewardRepository(retrofitServiceRewards)
    }

    override val notificationRepository: NotificationRepository by lazy {
        NetworkNotificationRepository(retrofitServiceNotifications)
    }
}