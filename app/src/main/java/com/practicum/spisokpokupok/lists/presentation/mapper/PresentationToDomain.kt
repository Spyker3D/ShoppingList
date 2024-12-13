package com.practicum.spisokpokupok.lists.presentation.mapper

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi

fun PurchaseListUi.toDomain() =
    ShoppingList(
        name = name,
        isCompleted = isCompleted,
        isFavorite = isAttached,
        id = id
    )