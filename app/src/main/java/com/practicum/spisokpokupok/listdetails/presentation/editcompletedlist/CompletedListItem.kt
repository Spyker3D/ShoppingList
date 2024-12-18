package com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun CompletedListItem(
    modifier: Modifier,
    task: Task
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(vertical = 14.dp)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { },
                enabled = false,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    disabledCheckedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    text = task.goodName,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    style = TextStyle(
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = task.quantity.toString(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " ",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = QuantityTypeToStringShort(task.quantityType),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 0.5.dp
        )
    }
}

private fun QuantityTypeToStringShort(quantityType: QuantityType): String {
    return when (quantityType) {
        QuantityType.KILOGRAM -> "кг"
        QuantityType.LITRE -> "л"
        QuantityType.PACK -> "уп"
        QuantityType.PIECE -> "шт"
        QuantityType.UNKNOWN -> "шт"
    }
}

@Composable
fun CompletedListAddItem(modifier: Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plus_ic),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Добавить продукт",
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun ListItemPreview() {
    ToDoListTheme {
        CompletedListItem(
            modifier = Modifier, task = Task(
                goodName = "Сухой корм",
                isCompleted = true,
                id = "123",
                quantity = 1,
                quantityType = QuantityType.KILOGRAM,
                position = 0
            )
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AddItemPreview() {
    ToDoListTheme {
        CompletedListAddItem(
            modifier = Modifier
        )
    }
}