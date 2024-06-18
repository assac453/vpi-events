package com.assac453.vpievents

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.assac453.vpievents.data.AppContainer
import com.assac453.vpievents.data.DefaultAppContainer

class VPIEventApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        createNotificationChannel()
    }
    private fun createNotificationChannel(){
        val id = getString(R.string.REGISTRATION_EVENT)
        val name = getString(R.string.REGISTRATION_EVENT)
        val descriptionText = getString(R.string.REGISTRATION_EVENT_DESCRIPTION)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}