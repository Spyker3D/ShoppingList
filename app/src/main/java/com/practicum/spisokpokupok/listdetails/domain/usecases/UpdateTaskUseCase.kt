package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import javax.inject.Inject

class UpdateTaskUseCase
    @Inject
    constructor(
        private val taskRepository: ShoppingTaskRepository,
    ) {
        suspend operator fun invoke(
            taskId: String,
            taskName: String,
            quantity: Int,
            quantityType: QuantityType,
            position: Int,
        ) {
            taskRepository.updateTask(
                taskId = taskId,
                goodName = taskName,
                quantity = quantity,
                quantityType = quantityType,
                position = position,
            )
        }
    }
