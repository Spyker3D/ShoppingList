package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import javax.inject.Inject

class GetCompletedListByIdUseCase
@Inject
constructor(
    private val taskRepository: ShoppingTaskRepository
) {
    suspend operator fun invoke(shoppingListId: String): ShoppingList =
        taskRepository.getCompletedListById(shoppingListId = shoppingListId)
}