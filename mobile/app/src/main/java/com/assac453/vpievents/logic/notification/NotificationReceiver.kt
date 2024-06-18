package com.assac453.vpievents.logic.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = InternalNotificationService.getInstance(context)
        service.showNotification(title = "sample", message = "from receiver")
    }
}