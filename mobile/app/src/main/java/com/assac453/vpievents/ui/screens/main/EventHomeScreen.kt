package com.assac453.vpievents.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.assac453.vpievents.ui.screens.main.helper.ErrorScreen
import com.assac453.vpievents.ui.screens.main.helper.LoadingScreen
import com.assac453.vpievents.ui.viewmodel.EventUiState

@Composable
fun EventHomeScreen(
    eventUiState: EventUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
){
    when(eventUiState){
        is EventUiState.Loading -> LoadingScreen(modifier)
        is EventUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
        is EventUiState.Success -> EventGridScreen(events = eventUiState.events, modifier= modifier, navController = navController)
    }
}