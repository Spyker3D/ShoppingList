package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun QuantityTypeChips(modifier: Modifier = Modifier) {
    val options = listOf("кг", "л", "уп", "шт")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = modifier.fillMaxWidth()) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.height(36.dp),
        ) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    modifier = Modifier.height(36.dp).width(200.dp),
                    selected = selectedOption == option,
                    onClick = { selectedOption = option },
                    shape =
                        SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size,
                        ),
                ) {
                    Text(text = option)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonExamplePreview() {
    ToDoListTheme {
        QuantityTypeChips(modifier = Modifier)
    }
}
