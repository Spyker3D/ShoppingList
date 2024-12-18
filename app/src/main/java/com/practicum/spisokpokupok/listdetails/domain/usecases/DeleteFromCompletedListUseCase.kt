package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import javax.inject.Inject

class DeleteFromCompletedListUseCase
@Inject
constructor(
    private val taskRepository: ShoppingTaskRepository,
) {
    suspend operator fun invoke(listId: String) {
        taskRepository.deleteCompletedList(listId)
    }
}