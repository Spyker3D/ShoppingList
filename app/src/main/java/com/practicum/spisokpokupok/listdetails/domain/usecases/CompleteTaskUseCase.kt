package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository

class CompleteTaskUseCase(
    private val taskRepository: ShoppingTaskRepository,
) {
    suspend operator fun invoke(taskId: String) {
        taskRepository.completeTask(taskId)
    }
}
