package com.practicum.spisokpokupok.lists.presentation.completedlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.GetCompletedListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompletedListViewModel @Inject
constructor(
    getCompletedListsUseCase: GetCompletedListsUseCase,
) : ViewModel() {

    private val _listStream = getCompletedListsUseCase()
    val listStream: StateFlow<List<ShoppingList>> =
        _listStream.stateIn(
            scope = viewModelScope,
            started =
            kotlinx.coroutines.flow.SharingStarted
                .WhileSubscribed(5000),
            initialValue = emptyList(),
        )
}