package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import com.practicum.buyinglist.R.drawable.ic_arrow_right
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

@Composable
fun TaskElement(
    onValueChange: (String) -> Unit,
    isRedacted: Boolean,
    name: String,
    quantity: String,
    quantityType: QuantityType,
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
                Box(
                    modifier =
                        Modifier.padding(
                            start = dimensionResource(id = R.dimen.horizontal_margin),
                        ),
                ) {
                    if (isRedacted) {
                        EditableTextField(
                            value = name,
                            onValueChange = onValueChange,
                            modifier = Modifier.height(56.dp),
                        )
                    } else {
                        Text(
                            text = name,
                            color = Black,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.alpha(0.5f),
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = quantity,
                        color = Black,
                    )
                    Text(
                        text =
                            when (quantityType) {
                                QuantityType.KILOGRAM -> {
                                    "кг"
                                }

                                QuantityType.LITRE -> {
                                    "л"
                                }

                                QuantityType.PACK -> {
                                    "уп"
                                }

                                QuantityType.PIECE -> {
                                    "шт"
                                }

                                QuantityType.UNKNOWN -> {
                                    ""
                                }
                            },
                        style = MaterialTheme.typography.bodySmall,
                        modifier =
                            Modifier.padding(
                                start = dimensionResource(id = R.dimen.horizontal_margin),
                            ),
                    )
                }
            }
            Icon(
                painter = painterResource(ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
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
        quantityType = QuantityType.PIECE,
        isRedacted = false,
        onValueChange = {},
    )
//
}