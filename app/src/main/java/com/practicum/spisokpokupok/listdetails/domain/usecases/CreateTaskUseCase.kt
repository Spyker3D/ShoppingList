package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import javax.inject.Inject

class CreateTaskUseCase
    @Inject
    constructor(
        private val taskRepository: ShoppingTaskRepository,
    ) {
        suspend operator fun invoke(
            listId: String,
            taskName: String,
            quantity: Int,
            quantityType: QuantityType,
            position: Int,
        ) {
            taskRepository.createTask(
                shoppingListId = listId,
                goodName = taskName,
                quantity = quantity,
                quantityType = quantityType,
                position = position,
            )
        }
    }
