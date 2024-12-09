package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import javax.inject.Inject

class UpdateFavoriteStatusUseCase
    @Inject
    constructor(
        private val shoppingListRepository: ShoppingListRepository,
    ) {
        suspend operator fun invoke(
            listId: String,
            isFavorite: Boolean,
        ) {
            if (isFavorite) {
                shoppingListRepository.addToFavorite(listId)
            } else {
                shoppingListRepository.removeFromFavorite(listId)
            }
        }
    }
