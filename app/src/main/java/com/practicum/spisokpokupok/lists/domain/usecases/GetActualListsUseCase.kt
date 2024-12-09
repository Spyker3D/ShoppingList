package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActualListsUseCase
    @Inject
    constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    operator fun invoke(): Flow<List<ShoppingList>> = shoppingListRepository.getCurrentLists()
}
