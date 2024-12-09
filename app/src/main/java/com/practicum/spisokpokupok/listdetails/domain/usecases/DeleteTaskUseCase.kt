package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository

class DeleteTaskUseCase(
    private val taskRepository: ShoppingTaskRepository,
) {
    suspend operator fun invoke(taskId: String) {
        taskRepository.deleteTask(taskId)
    }
}
