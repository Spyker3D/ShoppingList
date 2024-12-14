package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.practicum.spisokpokupok.navigation.CurrentListEdit
import kotlinx.coroutines.flow.MutableStateFlow

class CurrentLIstEditScreenViewModel(
    public val handle: SavedStateHandle,
) : ViewModel() {
    val currentListEdit = handle.toRoute<CurrentListEdit>()
    val listId = currentListEdit.id
    val _uiState = MutableStateFlow(CurrentListEditScreenUIState())
}
