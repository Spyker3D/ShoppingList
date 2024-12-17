package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import javax.inject.Inject

class MoveTaskToActualUseCase
    @Inject
    constructor(
        private val taskRepository: ShoppingTaskRepository,
    ) {
        suspend operator fun invoke(taskId: String) {
            taskRepository.moveTaskToActual(taskId)
        }
    }
