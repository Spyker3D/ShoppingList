package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import com.practicum.spisokpokupok.listdetails.presentation.newlist.BottomSheetState
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListItemUiState

data class CurrentListEditScreenUIState(
    val title: String = "Новый список",
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val productItems: List<NewListItemUiState> = emptyList(),
)
