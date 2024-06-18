package com.assac453.vpievents.data.repository

import com.assac453.vpievents.data.entity.Notification
import com.assac453.vpievents.network.service.NotificationService


interface NotificationRepository {
    suspend fun getNotifications(userId: String): List<Notification>
}

class NetworkNotificationRepository(
    private val notificationService: NotificationService
) : NotificationRepository {
    override suspend fun getNotifications(userId: String): List<Notification> {
        return notificationService.notificationsByUser(userId).map { item ->
            Notification(
                id = item.id,
                userId = item.userId,
                title = item.title,
                body = item.body,
                sentAt = item.sentAt,
                createdAt = item.createdAt,
                updatedAt = item.updatedAt,
            )
        }
    }
}