package com.practicum.spisokpokupok.listdetails.presentation.newlist

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

sealed interface NewListAction {
    data object OnTitleClick : NewListAction

    data object OnDeleteTitleClick : NewListAction

    data object OnAcceptTitleClick : NewListAction

    data class OnTaskClick(
        val index: Int,
    ) : NewListAction

    data object OnAddNewProduct : NewListAction

    data class OnTitleChange(
        val title: String,
    ) : NewListAction

    data class OnSaveTask(
        val index: Int,
    ) : NewListAction

    data object OnSaveList : NewListAction

    data class OnQuantityTypeChange(
        val position: Int,
        val quantityType: QuantityType,
    ) : NewListAction

    data class OnIncreaseClick(
        val position: Int,
    ) : NewListAction

    data class OnDecreaseClick(
        val position: Int,
    ) : NewListAction

    class OnTaskNameChange(
        val index: Int,
        val title: String,
    ) : NewListAction

    data object SaveTitle : NewListAction

    data class OnClearTaskNameClick(
        val index: Int,
    ) : NewListAction

    data class OnDeleteTaskClick(
        val itemIndex: Int,
    ) : NewListAction
}
