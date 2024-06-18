package com.assac453.vpievents.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.assac453.vpievents.VPIEventApplication
import com.assac453.vpievents.data.entity.Notification
import com.assac453.vpievents.data.repository.NotificationRepository
import com.assac453.vpievents.ui.graph.AppNavigation
import kotlinx.coroutines.launch


sealed interface NotificationUiState {
    data class Success(val notifications: List<Notification>) : NotificationUiState
    object Loading : NotificationUiState
    object Error : NotificationUiState
}

class NotificationViewModel(
    private val repository: NotificationRepository
) : ViewModel() {
    var notificationUiState: NotificationUiState by mutableStateOf(NotificationUiState.Loading)
        private set
    init {
        AppNavigation.User.user?.id?.let { getNotificationById(it) }
    }

    fun getNotificationById(id: String){
        Log.e("USER ID VIEW MODEL", id)
        viewModelScope.launch{
            notificationUiState = NotificationUiState.Loading
            notificationUiState = try {
                NotificationUiState.Success(repository.getNotifications(id))
            } catch (e: Exception){
                Log.e("TAG", e.stackTraceToString())
                NotificationUiState.Error
            }
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VPIEventApplication)
                val notificationRepository = application.container.notificationRepository
                NotificationViewModel(notificationRepository)
            }
        }
    }
}