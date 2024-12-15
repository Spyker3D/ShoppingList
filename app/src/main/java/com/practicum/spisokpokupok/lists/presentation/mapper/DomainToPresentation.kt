package com.practicum.spisokpokupok.lists.presentation.mapper

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi

fun ShoppingList.toPresentation() =
    PurchaseListUi(
        id = id,
        name = name,
        isCompleted = isCompleted,
        isAttached = isFavorite,
    )