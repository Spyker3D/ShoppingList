package com.practicum.spisokpokupok.listdetails.presentation.newlist

sealed interface NewListAction {
    data object OnTitleClick : NewListAction

    data class OnTaskClick(
        val index: Int,
    ) : NewListAction

    data object OnAddNewProduct : NewListAction

    data class OnTitleChange(
        val title: String,
    ) : NewListAction
}
