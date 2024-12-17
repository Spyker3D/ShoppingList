package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTasksUseCase
    @Inject
    constructor(
        private val shoppingTaskRepository: ShoppingTaskRepository,
    ) {
        operator fun invoke(listId: String): Flow<List<Task>> = shoppingTaskRepository.getCurrentTasks(listId)
    }
