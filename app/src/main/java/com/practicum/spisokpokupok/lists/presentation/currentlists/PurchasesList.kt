package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.lists.domain.model.PurchaseList
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.utils.SwipeToDeleteContainer

@Composable
fun PurchasesList(
    listOfPurchases: List<PurchaseList>,
    onListOfPurchases: (PurchaseList) -> Unit,
    onClickListener: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = listOfPurchases,
            key = { it.id }
        ) { purchase ->
            SwipeToDeleteContainer(
                item = purchase,
                onDelete = {
                    onListOfPurchases(purchase)
                },
//                onAttach = {
//                    purchase.isAttached = true
//                }
            ) {
                ItemCard(purchase, onClickListener)
            }
        }
    }
}

@Composable
fun ItemCard(purchaseList: PurchaseList, onClickListener: (String) -> Unit) {
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
fun PurchasesListPreview() {
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
            onDeleteItemListener = {}
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun ItemPreview() {
    ToDoListTheme {
        ItemCard(
            purchaseList = PurchaseList(
                id = "123",
                name = "Продукты"
            ),
            onClickListener = {}
        )
    }
}