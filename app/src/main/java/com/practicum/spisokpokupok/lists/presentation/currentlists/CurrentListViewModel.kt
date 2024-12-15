package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.DeleteFromActualListsUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.MoveToActualListUseCase
import com.practicum.spisokpokupok.lists.domain.usecases.UpdateFavoriteStatusUseCase
import com.practicum.spisokpokupok.lists.presentation.mapper.toPresentation
import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
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
    private val deleteFromActualListsUseCase: DeleteFromActualListsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    var state by mutableStateOf(MviState())
        private set
//
//    val actionsFlow: SharedFlow<MviAction> = MutableSharedFlow<MviAction>().map {
//        when (it) {
//            MviAction.LoadList -> state.copy(getActualListsUseCase())
//        }
//    }

    private var listId = ""
    private val _listStream = getActualListsUseCase()
    val listStream: StateFlow<List<PurchaseListUi>> =
        _listStream.map { shoppingList ->
            shoppingList.map { it.toPresentation() }
        }
            .stateIn(
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

//    private fun loadShoppingList {
//        state.shoppingList?.let {  }
//    }
//
//    // Function to handle swipe reveal
//    fun revealItem(id: String) {
//        _purchasesList.value = _purchasesList.value.map {
//            it.copy(isOptionsRevealed = it.id == id)
//        }
//    }
//
//    // Function to reset reveal on action
//    fun resetReveal() {
//        _purchasesList.value = _purchasesList.value.map {
//            it.copy(isOptionsRevealed = false)
//        }
//    }

    init {   // для тестирования списков !ПОТОМ УДАЛИТЬ!
        repeat(5) { index ->
            addList(listName = "Cписок $index")
        }
    }
}
