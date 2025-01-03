package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActualListsUseCase
@Inject
constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    operator fun invoke(): Flow<List<ShoppingList>> {
        return shoppingListRepository.getCurrentLists().map { shoppingList ->
            shoppingList.sortedWith(
                compareByDescending<ShoppingList> { it.isFavorite }
                    .thenBy { it.name.lowercase() }
            )
        }
    }
}