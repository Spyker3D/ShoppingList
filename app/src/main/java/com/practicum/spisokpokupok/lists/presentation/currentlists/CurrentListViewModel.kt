package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentListViewModel
    @Inject
    constructor(
        private val createListUseCase: CreateListUseCase,
        private val getActualListsUseCase: GetActualListsUseCase,
    ) : ViewModel() {
        private val _listStream = MutableStateFlow<List<ShoppingList>>(emptyList())
        val listStream = _listStream

        fun fetchLists() {
            viewModelScope.launch {
                getActualListsUseCase().collect {
                    _listStream.update { list ->
                        list.sortedBy { it.isCompleted }
                    }
                }
            }
        }

        fun addList(listName: String) {
            viewModelScope.launch {
                createListUseCase(listName)
            }
        }
    }
