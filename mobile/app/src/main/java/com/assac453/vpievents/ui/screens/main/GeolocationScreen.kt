package com.assac453.vpievents.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeolocationScreen(){
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Вычислить геолокацию")}) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Geolocation screen")
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Get your location")
            }
        }
    }
}