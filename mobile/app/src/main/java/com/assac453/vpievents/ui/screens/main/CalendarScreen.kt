package com.assac453.vpievents.ui.screens.main

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
import com.assac453.vpievents.ui.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController){
    val eventViewModel : EventViewModel =
        viewModel(factory = EventViewModel.factory)

    Scaffold(
        topBar = {TopAppBar(title = { Text(text = "Календарь")})}
    ) {
        Surface (modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            EventHomeScreen(eventUiState = eventViewModel.eventUiState, retryAction = {eventViewModel::getEventByType}, navController = navController)
        }
    }
}
