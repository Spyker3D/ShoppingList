package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.ui.theme.blue
import com.practicum.spisokpokupok.ui.theme.cyan

@Composable
fun CurrentPurchasesListScreen(
    modifier: Modifier = Modifier,
    onNavigateToNewList: () -> Unit,
    onItemClicked: (String) -> Unit,
    shoppingList: List<PurchaseListUi>,
    onDeleteItem: (String) -> Unit,
    onFavoriteItem: (String, Boolean) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBar(modifier = modifier, purchasesList = shoppingList)
        },
        content = { paddingValues ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                        ),
            ) {
                if (shoppingList.isNotEmpty()) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(
                                    brush =
                                        Brush.verticalGradient(
                                            colors =
                                                listOf(
                                                    Color.Transparent,
                                                    MaterialTheme.colorScheme.surface,
                                                ),
                                        ),
                                ),
                    ) {
                        Icon(
                            modifier =
                                Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 48.dp)
                                    .size(250.dp),
                            painter = painterResource(id = R.drawable.img_bags),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (shoppingList.isNotEmpty()) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                        ) {
                            PurchasesListSwipe(
                                listOfPurchases = shoppingList,
                                onClickListener = onItemClicked,
                                onDeleteItemListener = {
                                    onDeleteItem(it)
                                },
                                onFavoriteItemListener = onFavoriteItem,
                            )
                        }
                    } else {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                                    .background(Color.Transparent),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    text = "У вас еще нет списков покупок. Самое время создать первый!",
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                )
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.End),
                                    contentAlignment = Alignment.CenterEnd,
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(end = 32.dp),
                                        painter = painterResource(id = R.drawable.ic_new_list_arrow),
                                        tint = blue,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(onNavigateToNewList = onNavigateToNewList)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    purchasesList: List<PurchaseListUi>,
) {
    LargeTopAppBar(
        modifier =
            modifier
                .padding(top = 0.dp)
                .wrapContentHeight(),
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 0.dp),
                text =
                    if (purchasesList.isNotEmpty()) {
                        "Все ваши списки"
                    } else {
                        "Все ваши списки здесь"
                    },
                fontSize = 44.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                lineHeight = 44.sp,
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onTertiary,
            ),
    )
}

@Composable
private fun BottomBar(onNavigateToNewList: () -> Unit) {
    BottomAppBar(
        modifier =
            Modifier
                .padding(horizontal = 16.dp)
                .height(164.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_back),
                    tint = cyan,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.width(200.dp),
                    text = "Проведите, чтобы открыть завершенные списки",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_forward),
                    tint = cyan,
                    contentDescription = null,
                )
            }
            Icon(
                modifier =
                    Modifier
                        .padding(bottom = 24.dp)
                        .size(48.dp)
                        .clickable { onNavigateToNewList() },
                painter = painterResource(id = R.drawable.ic_add),
                tint = cyan,
                contentDescription = null,
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun CurrentPurchasesListScreenScaffoldPreview() {
    ToDoListTheme {
        CurrentPurchasesListScreen(
            onNavigateToNewList = {},
            onItemClicked = {},
            onDeleteItem = {},
            onFavoriteItem = { id, isFavorite ->
                println("Item $id marked as favorite: $isFavorite")
            },
            shoppingList =
                listOf(
                    PurchaseListUi(
                        id = "123",
                        name = "Продукты",
                        isAttached = true,
                        isOptionsRevealed = false,
                    ),
                    PurchaseListUi(
                        id = "111",
                        name = "Канцтовары",
                        isOptionsRevealed = false,
                    ),
                    PurchaseListUi(
                        id = "11100",
                        name = "Еда для животных",
                        isOptionsRevealed = false,
                    ),
                    PurchaseListUi(
                        id = "1111200",
                        name = "Еда для людей",
                        isOptionsRevealed = false,
                    ),
                ),
        )
    }
}
