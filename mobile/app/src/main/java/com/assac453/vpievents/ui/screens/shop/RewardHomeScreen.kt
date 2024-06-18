package com.assac453.vpievents.ui.screens.shop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.assac453.vpievents.ui.screens.main.helper.ErrorScreen
import com.assac453.vpievents.ui.screens.main.helper.LoadingScreen
import com.assac453.vpievents.ui.viewmodel.RewardUiState

@Composable
fun RewardHomeScreen(
    rewardUiState: RewardUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
){
    when(rewardUiState){
        is RewardUiState.Error -> ErrorScreen(retryAction = retryAction)
        is RewardUiState.Loading -> LoadingScreen()
        is RewardUiState.Success -> RewardGridScreen(rewards = rewardUiState.reward, modifier = modifier, navController = navController)
        else -> {}
    }
        
}