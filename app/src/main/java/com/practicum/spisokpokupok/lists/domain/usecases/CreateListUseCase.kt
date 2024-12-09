package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class CreateListUseCase
    @Inject
    constructor(
        private val shoppingListRepository: ShoppingListRepository,
    ) {
        suspend operator fun invoke(listName: String) = shoppingListRepository.createList(listName)
    }
