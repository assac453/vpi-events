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
import com.assac453.vpievents.data.entity.Event

import com.assac453.vpievents.data.repository.EventRepository
import kotlinx.coroutines.launch


sealed interface EventUiState{
    data class Success(val events: List<Event>) : EventUiState
    object Loading: EventUiState
    object Error: EventUiState
}


class EventViewModel(
    private val repository: EventRepository
): ViewModel() {

    var eventUiState : EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    init {
        getEventByType()
    }

    fun getEventByType(type: String = ""){
        viewModelScope.launch{
            eventUiState = EventUiState.Loading
            eventUiState = try {
                EventUiState.Success(repository.getEvents(type))
            } catch (e: Exception){
                Log.e("TAG", e.stackTraceToString())
                EventUiState.Error
            }
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VPIEventApplication)
                val eventRepository = application.container.eventRepository
                EventViewModel(eventRepository)
            }
        }
    }
}