package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteFromActualListsUseCase
    @Inject
    constructor(
        private val shoppingListRepository: ShoppingListRepository,
    ) {
        suspend operator fun invoke(listId: String) {
            shoppingListRepository.deleteList(listId)
        }
    }
