package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun EditableTextField(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier,
    label: String = "Название",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle =
        TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
        ),
    isValid: Boolean = true,
) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        modifier =
            Modifier
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun EditableTextFieldPreview() {
    EditableTextField(
        onValueChange = {},
        value = "",
        modifier = Modifier,
    )
}
