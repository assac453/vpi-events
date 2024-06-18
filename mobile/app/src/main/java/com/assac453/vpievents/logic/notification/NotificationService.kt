package com.assac453.vpievents.logic.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.assac453.vpievents.MainActivity
import com.assac453.vpievents.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToFirebase(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MESSAGE", message.notification.toString())
        message.notification?.let {
            showNotification(it.title ?: "", it.body ?: "")
        }
    }

    fun showNotification(title: String, message: String) {
        var channel: NotificationChannel = NotificationChannel(
            getString(R.string.REGISTRATION_EVENT),
            getString(R.string.REGISTRATION_EVENT),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        var notification: Notification = NotificationCompat.Builder(this, "TEST_CHANNEL")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        notificationManager.notify(42, notification)
    }

    private fun sendTokenToFirebase(token: String) {

    }
}

class InternalNotificationService private constructor(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    companion object {
        @Volatile
        private var instance: InternalNotificationService? = null

        fun getInstance(context: Context): InternalNotificationService {
            return instance ?: synchronized(this) {
                instance ?: InternalNotificationService(context).also { instance = it }
            }
        }
    }

    fun showNotification(context: Context = this.context, title: String, message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, NotificationReceiver::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )


//        var channel: NotificationChannel = NotificationChannel(getString(context, R.string.REGISTRATION_EVENT), getString(context, R.string.REGISTRATION_EVENT), NotificationManager.IMPORTANCE_DEFAULT)
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
        var notification: Notification = NotificationCompat.Builder(context, "TEST_CHANNEL")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Notification", notificationIntent)
            .build()
        notificationManager.notify(42, notification)
    }
}