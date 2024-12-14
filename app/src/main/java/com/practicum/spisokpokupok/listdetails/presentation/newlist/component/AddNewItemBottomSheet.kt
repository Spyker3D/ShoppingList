package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun AddNewItemBottomSheet(
    modifier: Modifier = Modifier,
    counter: String,
    onIncreeseClick: () -> Unit,
    onDecreeseClick: () -> Unit,
    onApproveClick: () -> Unit,
) {
    Column {
        Spacer(
            modifier = Modifier.padding(vertical = 4.dp),
        )
        QuantityTypeChips()
        Spacer(
            modifier = Modifier.padding(vertical = 20.dp),
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.counter_minus_ic),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier =
                    modifier.clickable {
                        onDecreeseClick
                    },
            )
            Text(text = counter)
            Icon(
                painter = painterResource(id = R.drawable.counter_plus_ic),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier =
                    modifier.clickable {
                        onIncreeseClick
                    },
            )
        }

        Spacer(modifier = modifier.padding(top = 32.dp))
        Button(
            colors =
                ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                ),
            modifier = modifier.fillMaxWidth(),
            onClick = onApproveClick,
        ) {
            Text("Готово")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true)
@Composable
private fun AddNewItemBottomPreview(modifier: Modifier = Modifier.fillMaxWidth()) {
    ToDoListTheme {
        AddNewItemBottomSheet(
            counter = "1",
            onDecreeseClick = {},
            onIncreeseClick = {},
            onApproveClick = {},
        )
    }
}
