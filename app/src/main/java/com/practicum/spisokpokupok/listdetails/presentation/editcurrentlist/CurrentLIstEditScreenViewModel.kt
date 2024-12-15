package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.practicum.spisokpokupok.navigation.CurrentListEditRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrentLIstEditScreenViewModel(
    public val handle: SavedStateHandle,
) : ViewModel() {
    val currentListEditRoute = handle.toRoute<CurrentListEditRoute>()
    val listId = currentListEditRoute.id
    val _uiState = MutableStateFlow(CurrentListEditScreenUIState())
    val uiState = _uiState.asStateFlow()
}
