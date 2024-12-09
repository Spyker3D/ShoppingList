package com.practicum.spisokpokupok.lists.domain.usecases

import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository

class UpdateFavoriteStatusUseCase(
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
