package com.practicum.spisokpokupok.lists.presentation.currentlists

interface MviAction {  // enum?
    data object DeleteItem: MviAction
    data object MakeItemFavorite: MviAction
    data object ClickItem : MviAction
    data object GoToCompletedLists: MviAction
    data object GoToNewListScreen: MviAction
}