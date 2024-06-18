package com.assac453.vpievents.ui.screens.profile.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assac453.vpievents.data.entity.Notification
import com.assac453.vpievents.ui.graph.AppNavigation
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotificationProfileScreen(
    notifications: List<Notification>,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val sortedNotifications = notifications.sortedBySentAt()

    LazyVerticalGrid(columns = GridCells.Adaptive(300.dp), contentPadding = PaddingValues(10.dp)) {
        itemsIndexed(sortedNotifications) { _, notification ->
            NotificationCard(
                notification = notification,
                modifier = modifier,
                navController = navController
            )
        }
    }
}

fun String.toDateLong(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.parse(this)?.time ?: 0
}

fun List<Notification>.sortedBySentAt(): List<Notification> {
    return this.sortedByDescending { it.sentAt?.toDateLong() }
}


@Composable
fun NotificationCard(
    notification: Notification,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ) {
    val fontSize = 12.sp
    Card(
        modifier = modifier
            .padding(6.dp)
            .fillMaxSize()
            ,
        onClick = {
            AppNavigation.NotificationContainer.selectedNotification = notification
            navController.navigate(AppNavigation.NOTIFICATION_DESCRIPTION)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                notification.title?.let { string ->
                    Text(text = string, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier.size(15.dp))
                notification.body?.let { string ->
                    Text(text = string.take(100).plus("..."), fontSize = 14.sp)
                }
                Spacer(modifier.size(15.dp))
                notification.sentAt?.let { string ->
                    Text(text = string, fontSize = 12.sp)
                }
            }
        }
    }
}