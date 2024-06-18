package com.assac453.vpievents.ui.screens.profile.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.viewmodel.NotificationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavHostController){
    val notificationViewModel :NotificationViewModel =
        viewModel(factory = NotificationViewModel.factory)

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Уведомления") }) }
    ) {
        Surface (modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            NotificationHomeScreen(notificationUiState = notificationViewModel.notificationUiState, retryAction = { AppNavigation.User.user?.id?.let { it1 ->
                notificationViewModel.getNotificationById(it1)
            } }, navController = navController)
        }
    }
}
