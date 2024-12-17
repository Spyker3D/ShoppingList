package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditableTextField(
    modifier: Modifier,
    index: Int,
    value: String,
    onValueChange: (String) -> Unit,
) {
    EnhancedTextFieldExample(
        modifier = modifier,
        text = value,
        onValueChange = onValueChange,
    )
}

@Composable
fun EnhancedTextFieldExample(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier =  modifier.fillMaxWidth(),
        value = text,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun EditableTextFieldPreview() {
    EditableTextField(
        index = 0,
        onValueChange = {},
        value = "",
        modifier = Modifier,
    )
}
