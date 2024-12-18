package com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.usecases.DeleteFromCompletedListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.GetCompletedListByIdUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.GetListOfItemsUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.MoveToActualListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    var listOfItems by mutableStateOf<List<Task>>(emptyList())
        private set
    var listName by mutableStateOf<String>("")
        private set

    fun getListOfItemsById(shoppingListId: String) {
        viewModelScope.launch {
            listOfItems = getListOfItemsUseCase(shoppingListId)
        }
    }

    fun getListName(shoppingListId: String) {
        viewModelScope.launch {
            listName = getCompleletdListByIdUseCase(shoppingListId).name
        }
    }

    fun moveFromCompletedToActualLists(listId: String) {
        viewModelScope.launch {
            deleteFromCompletedListUseCase(listId)
            moveToActualListUseCase(listId)
        }
    }
}
