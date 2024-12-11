package com.practicum.spisokpokupok.lists.presentation.completedlists

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.lists.domain.model.PurchaseList
import com.practicum.spisokpokupok.lists.presentation.currentlists.PurchasesList
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.ui.theme.blue
import com.practicum.spisokpokupok.ui.theme.cyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedPurchasesListScreen(
    onNavigateToNewList: () -> Unit,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
//    val purchasesList = remember {    // для тестирования UI без списков
//        mutableStateListOf<PurchaseList>()
//    }

    val purchasesList = remember {
        mutableStateListOf(
            PurchaseList(
                id = "123",
                name = "Продукты",
                isAttached = true
            ),
            PurchaseList(
                id = "111",
                name = "Канцтовары"
            ),
            PurchaseList(
                id = "1100",
                name = "Еда для животных"
            )
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            LargeTopAppBar(
                modifier = modifier
                    .wrapContentHeight(),
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 0.dp),
                        text = "Завершенные списки",
                        fontSize = 44.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        lineHeight = 44.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 28.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (purchasesList.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            PurchasesList(
                                listOfPurchases = purchasesList,
                                onClickListener = onItemClicked,
                                onListOfPurchases = {
                                    purchasesList -= it
                                }
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .padding(bottom = 48.dp)
                                .height(113.dp)
                                .width(250.dp)
                                .align(Alignment.CenterHorizontally),
                            painter = painterResource(id = R.drawable.img_bags),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    text = "У вас еще нет списков покупок. Самое время создать первый!",
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular))
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.End),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(end = 32.dp),
                                        painter = painterResource(id = R.drawable.ic_new_list_arrow),
                                        tint = blue,
                                        contentDescription = null
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
        }
    )
}

@Composable
private fun BottomBar(onNavigateToNewList: () -> Unit) {
    BottomAppBar(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(164.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_back),
                    tint = cyan,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.width(200.dp),
                    text = "Проведите, чтобы открыть все списки",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    style = MaterialTheme.typography.bodySmall
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_forward),
                    tint = cyan,
                    contentDescription = null
                )
            }
            Icon(
                modifier = Modifier
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
fun CompletedPurchasesListScreenPreview() {
    ToDoListTheme {
        CompletedPurchasesListScreen(
            onNavigateToNewList = {},
            onItemClicked = {}
        )
    }
}