package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository

class MoveToActualListUseCase(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(listId: String) {
        shoppingListRepository.moveToActualLists(listId)
    }
}
