package com.practicum.spisokpokupok.listdetails.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class GetListTitleUseCase
    @Inject
    constructor(
        private val repository: ShoppingListRepository,
    ) {
        suspend operator fun invoke(listId: String): String = repository.getListTitle(listId)
    }
