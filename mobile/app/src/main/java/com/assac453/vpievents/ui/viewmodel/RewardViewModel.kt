package com.assac453.vpievents.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.assac453.vpievents.VPIEventApplication
import com.assac453.vpievents.data.entity.Reward
import com.assac453.vpievents.data.repository.RewardRepository
import kotlinx.coroutines.launch

sealed interface RewardUiState {
    data class Success(val reward: List<Reward>) : RewardUiState
    object Loading : RewardUiState
    object Error : RewardUiState
}


class RewardViewModel(
    val repository: RewardRepository
) : ViewModel() {
    var rewardUiState : RewardUiState by mutableStateOf(RewardUiState.Loading)
        private set
    init {
        getRewards()
    }
    fun getRewards(status: String = "В наличии") {
        viewModelScope.launch {
            rewardUiState = RewardUiState.Loading
            rewardUiState = try {
                RewardUiState.Success(repository.getRewards(status))
            } catch (e: Exception){
                e.message?.let { Log.e("ERROR", it) }
                RewardUiState.Error
            }
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VPIEventApplication)
                val rewardRepository = application.container.rewardRepository
                RewardViewModel(rewardRepository)
            }
        }
    }
}