package com.practicum.spisokpokupok.listdetails.presentation.success

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    listName: String,
    onNavigateToCompletedList: () -> Unit,
    onBackPressed: () -> Unit
) {
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp,
                top = 12.dp
            )
            .padding(start = 16.dp, end = 16.dp, top = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = listName,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bag_success),
                contentDescription = null,
            )
            Text(
                text = "Поздравляем!",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Вы купили все из списка продуктов. Возвращайтесь снова с новыми списками.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = onNavigateToCompletedList,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Перейти на главную",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )
        }
    }
}


@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun SuccessScreenPreview() {
    ToDoListTheme {
        SuccessScreen(
            listName = "Ингредиенты",
            onNavigateToCompletedList = {},
            onBackPressed = {}
        )
    }
}