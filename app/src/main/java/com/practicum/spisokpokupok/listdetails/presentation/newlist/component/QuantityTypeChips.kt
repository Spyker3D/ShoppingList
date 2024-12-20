package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun QuantityTypeChips(
    modifier: Modifier = Modifier,
    selectedOption: QuantityType,
    onOptionSelected: (QuantityType) -> Unit,
) {
    val options: List<QuantityType> =
        listOf(
            QuantityType.KILOGRAM,
            QuantityType.LITRE,
            QuantityType.PACK,
            QuantityType.PIECE,
        )

    Column(modifier = modifier.fillMaxWidth()) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.height(36.dp),
        ) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    colors =
                        SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.surface,
                            activeContentColor = MaterialTheme.colorScheme.onSurface,
                            inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            inactiveContentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    modifier =
                        Modifier
                            .height(36.dp)
                            .width(200.dp),
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    shape =
                        SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size,
                        ),
                    icon = {},
                ) {
                    Text(
                        text = option.abbreviation,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonExamplePreview() {
    ToDoListTheme {
        QuantityTypeChips(
            selectedOption = QuantityType.PIECE,
            onOptionSelected = {},
        )
    }
}
