package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

sealed interface ListEditAction {
    data class OnTaskClick(
        val index: Int,
    ) : ListEditAction

    data object OnAddNewProduct : ListEditAction

    data object OnSaveTask : ListEditAction

    data object OnChooseAllItems : ListEditAction

    data class OnQuantityTypeChange(
        val position: Int,
        val quantityType: QuantityType,
    ) : ListEditAction

    data object OnDeleteCompletedTasks : ListEditAction

    data class OnEncreaseClick(
        val position: Int,
    ) : ListEditAction

    data class OnDecreaseClick(
        val position: Int,
    ) : ListEditAction

    data class OnDeleteClick(
        val position: Int,
    ) : ListEditAction

    data class OnCheckClick(
        val index: Int,
    ) : ListEditAction

    data object CompleteList : ListEditAction

    class OnTaskNameChange(
        val index: Int,
        val title: String,
    ) : ListEditAction

    data class OnClearTaskNameClick(
        val index: Int,
    ) : ListEditAction
}
