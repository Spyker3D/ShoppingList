package com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.usecases.CompleteListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.DeleteFromCompletedListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.GetCompletedListByIdUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.GetListOfItemsUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.MoveToActualListUseCase
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCompletedListViewModel
@Inject
constructor(
    private val getListOfItemsUseCase: GetListOfItemsUseCase,
    private val getCompleletdListByIdUseCase: GetCompletedListByIdUseCase,
    private val deleteFromCompletedListUseCase: DeleteFromCompletedListUseCase,
    private val moveToActualListUseCase: MoveToActualListUseCase,
    state: SavedStateHandle
) : ViewModel() {

    var listOfItems by mutableStateOf<List<Task>>(emptyList())
        private set
    var listName by mutableStateOf<String>("")
        private set
    private val listId: String = state.get<String>("listId")!!

    init {
        viewModelScope.launch {
            listOfItems = getListOfItemsUseCase(listId)
            listName = getCompleletdListByIdUseCase(listId).name
        }
    }

    fun moveFromCompletedToActualLists(listId: String) {
        viewModelScope.launch {
            deleteFromCompletedListUseCase(listId)
            moveToActualListUseCase(listId)
        }
    }
}
