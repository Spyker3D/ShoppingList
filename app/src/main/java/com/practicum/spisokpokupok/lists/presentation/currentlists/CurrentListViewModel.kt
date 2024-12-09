package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.MoveToActualListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentListViewModel
    @Inject
    constructor(
        private val createListUseCase: CreateListUseCase,
        getActualListsUseCase: GetActualListsUseCase,
        private val moveToActualListUseCase: MoveToActualListUseCase,
    ) : ViewModel() {
        private var listId = ""
        private val _listStream = getActualListsUseCase()
        val listStream: StateFlow<List<ShoppingList>> =
            _listStream.stateIn(
                scope = viewModelScope,
                started =
                    kotlinx.coroutines.flow.SharingStarted
                        .WhileSubscribed(5000),
                initialValue = emptyList(),
            )

        fun addList(listName: String) {
            viewModelScope.launch {
                listId = createListUseCase(listName)
                moveToActualListUseCase(listId)
            }
        }
    }
