package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R.drawable.ic_arrow_right
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

@Composable
fun TaskElement(
    onValueChange: (String) -> Unit,
    isRedacted: Boolean,
    name: String,
    errorMesage: String,
    isError: Boolean,
    quantity: String,
    quantityType: QuantityType,
    onElementClick: () -> Unit,
    modifier: Modifier,
    onClearClick: () -> Unit,
) {
    Column(
        modifier = modifier.height(72.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                modifier
                    .fillMaxWidth()
                    .clickable { onElementClick() },
        ) {
            NewTaskEditableTextField(
                value = name,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                errorMessage = errorMesage,
                onClearClick = onClearClick,
                isRedacted = isRedacted,
                quantity = quantity,
                quantityType = quantityType,
            )
        }
        Icon(
            painter = painterResource(ic_arrow_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.alpha(0.5f),
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
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
        onClearClick = {},
        isError = true,
        errorMesage = "fasdf",
    )
}
