package com.practicum.spisokpokupok.lists.presentation.currentlists

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.utils.ActionIcon
import com.practicum.spisokpokupok.utils.SwipeableRightItem


@Composable
fun PurchasesListSwipe(
    listOfPurchases: List<PurchaseListUi>,
    onDeleteItemListener: (String) -> Unit,
    onClickListener: (String) -> Unit,
    onFavoriteItemListener: ((String, Boolean) -> Unit)? = null
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(
            items = listOfPurchases,
        ) { index, purchase ->
            SwipeableRightItem(
                isRevealed = purchase.isOptionsRevealed,
                onExpanded = {
//                    listOfPurchases.toMutableList()[index] =
//                        purchase.copy(isOptionsRevealed = true)  // по индексу - как достать . toTypedArray?
                    // как сделать признак, чтобы при раскрытии признак "Видны ли иконки" становился true
                },
                onCollapsed = {
//                    listOfPurchases.toMutableList()[index] = purchase.copy(isOptionsRevealed = false)
                    // как сделать признак, чтобы при сворачивании признак "Видны ли иконки" становился false
                },
                actions = {
                    ActionIcon(
                        onClick = {
                            onDeleteItemListener(purchase.id)
                        },
                        backgroundColor = MaterialTheme.colorScheme.onError,
                        icon = painterResource(id = R.drawable.ic_delete_blue),
                        modifier = Modifier.fillMaxHeight()
                    )
                    ActionIcon(
                        onClick = {
                            onFavoriteItemListener?.invoke(purchase.id, !purchase.isAttached)
//                            listOfPurchases.toMutableList()[index] = purchase.copy(
//                                isAttached = true, isOptionsRevealed = false
//                            )
                            // как сделать признак, чтобы при раскрытии признак "Видны ли иконки" становился false и иконки сворачивались
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        icon = painterResource(id = R.drawable.ic_blue_paperclip),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            ) {
                ItemCardSwipe(purchase, onClickListener)
            }
        }
    }
}

@Composable
fun ItemCardSwipe(purchaseList: PurchaseListUi, onClickListener: (String) -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClickListener(purchaseList.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (purchaseList.isAttached && !purchaseList.isCompleted) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_paperclip),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            }
            if (purchaseList.isCompleted) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            }
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = purchaseList.name,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                color = if (purchaseList.isAttached) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onTertiary
                }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null
        )
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 0.5.dp
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PurchasesListSwipePreview() {
    ToDoListTheme {
        PurchasesListSwipe(
            listOfPurchases = mutableListOf(
                PurchaseListUi(
                    id = "123",
                    name = "Продукты",
                    isAttached = true,
                    isOptionsRevealed = false
                ),
                PurchaseListUi(
                    id = "111",
                    name = "Канцтовары",
                    isOptionsRevealed = false
                ),
                PurchaseListUi(
                    id = "11100",
                    name = "Еда для животных",
                    isOptionsRevealed = false

                )
            ),
            onClickListener = {},
            onDeleteItemListener = {},
            onFavoriteItemListener = null
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun ItemPreviewSwipe() {
    ToDoListTheme {
        ItemCardSwipe(
            purchaseList = PurchaseListUi(
                id = "123",
                name = "Продукты",
                isOptionsRevealed = false
            ),
            onClickListener = {}
        )
    }
}