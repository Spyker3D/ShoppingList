package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R.drawable.ic_arrow_right

@Composable
fun TaskElement(
    name: String,
    quantity: String,
    quantityType: String,
    onElementClick: () -> Unit,
    modifier: Modifier,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .clickable { onElementClick() },
        ) {
            Column {
                Text(
                    text = name,
                    color = Black,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.alpha(0.5f),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = quantity,
                        color = Black,
                    )
                    Text(
                        text = quantityType,
                        color = Black,
                    )
                }
            }
            Icon(
                painter = painterResource(ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.alpha(0.5f),
            )
        }
    }
}

@Preview
@Composable
private fun TaskElementPreview() {
    TaskElement(
        name = "Огурцы",
        onElementClick = {},
        modifier = Modifier,
        quantity = "1",
        quantityType = "кг",
    )
//
}
