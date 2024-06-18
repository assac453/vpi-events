package com.assac453.vpievents.ui.screens.profile.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.assac453.vpievents.ui.screens.main.helper.ErrorScreen
import com.assac453.vpievents.ui.screens.main.helper.LoadingScreen
import com.assac453.vpievents.ui.viewmodel.NotificationUiState

@Composable
fun NotificationHomeScreen(
    notificationUiState: NotificationUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    when(notificationUiState){
        is NotificationUiState.Loading -> LoadingScreen(modifier)
        is NotificationUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
        is NotificationUiState.Success -> NotificationProfileScreen(notifications = notificationUiState.notifications, modifier = modifier, navController = navController)
    }
}