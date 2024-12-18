package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.listdetails.domain.usecases.CompleteListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.MoveToActualListUseCase
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.DeleteFromActualListsUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentListViewModel
@Inject
constructor(
    getActualListsUseCase: GetActualListsUseCase,
    private val deleteFromActualListsUseCase: DeleteFromActualListsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
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

    fun deleteList(listId: String) {
        viewModelScope.launch {
            deleteFromActualListsUseCase(listId)
        }
    }

    fun updateFavoriteStatus(listId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(listId = listId, isFavorite = isFavorite)
        }
    }
}
