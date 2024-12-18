package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class MoveToActualListUseCase
    @Inject
    constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(listId: String) {
        shoppingListRepository.moveToActualLists(listId)
    }
}
