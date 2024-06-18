package com.assac453.vpievents.ui.screens.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.assac453.vpievents.ui.viewmodel.RewardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopGridScreen(navController: NavHostController) {
    val rewardViewModel: RewardViewModel = viewModel(factory = RewardViewModel.factory)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = {
        Column {
            Text(text = "Магазин")
            Text(text = "Здесь вы можете обменять баллы, которые заработаете за посещение мероприятий", style = TextStyle(fontSize = 14.sp))
        }

        }) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            RewardHomeScreen(
                rewardUiState = rewardViewModel.rewardUiState,
                retryAction = { rewardViewModel.getRewards() },
                navController = navController
            )
        }
    }
}