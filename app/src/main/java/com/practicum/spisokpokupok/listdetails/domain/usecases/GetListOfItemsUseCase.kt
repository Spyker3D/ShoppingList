package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import javax.inject.Inject

class GetListOfItemsUseCase
@Inject
constructor(
    private val taskRepository: ShoppingTaskRepository
) {
    suspend operator fun invoke(shoppingListId: String): List<Task> =
        taskRepository.getShoppingTasksWithGoods(shoppingListId = shoppingListId)
}