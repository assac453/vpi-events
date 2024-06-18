package com.assac453.vpievents.ui.screens.profile.notifications

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assac453.vpievents.data.entity.Notification
import com.assac453.vpievents.ui.graph.AppNavigation


@Composable
fun NotificationFullScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    val notification: Notification? = AppNavigation.NotificationContainer.selectedNotification
    Scaffold {
        Card(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .minimumInteractiveComponentSize()
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),

            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(15.dp)

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd // This aligns the content to the top end (right)
                ) {
                    Text(text = notification?.sentAt.toString())
                }
                Text(
                    text = "${notification?.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "${notification?.body}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

            }
        }
    }
}