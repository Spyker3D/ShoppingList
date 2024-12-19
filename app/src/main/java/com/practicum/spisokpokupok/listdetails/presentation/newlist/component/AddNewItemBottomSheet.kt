package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun AddNewItemBottomSheet(
    quantityType: QuantityType,
    counter: String,
    onQuantityTypeChange: (QuantityType) -> Unit,
    onEncreeseClick: () -> Unit,
    onDecreeseClick: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Column {
        Spacer(
            modifier = Modifier.padding(vertical = 4.dp),
        )
        QuantityTypeChips(
            selectedOption = quantityType,
            onOptionSelected = { onQuantityTypeChange(it) },
        )
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
                        onDecreeseClick()
                    },
            )
            Text(text = counter)
            Icon(
                painter = painterResource(id = R.drawable.counter_plus_ic),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier =
                    modifier.clickable {
                        onEncreeseClick()
                    },
            )
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
            onQuantityTypeChange = {},
            quantityType = QuantityType.PIECE,
            onEncreeseClick = {},
        )
    }
}
