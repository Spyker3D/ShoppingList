package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class CompleteListUseCase
@Inject
constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(listId: String) {
        shoppingListRepository.completeList(listId)
    }
}
