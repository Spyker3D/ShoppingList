package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditableTextField(
    modifier: Modifier = Modifier,
    index: Int,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    EnhancedTextFieldExample()
}
@Composable
fun EnhancedTextFieldExample() {
    val text = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Label") },
        placeholder = { Text("Enter text") },
        leadingIcon = { Icon(Icons.Filled.Info, contentDescription = "Info") },
        trailingIcon = {
            IconButton(onClick = { text.value = "" }) {
                Icon(Icons.Filled.Clear, contentDescription = "Clear")
            }
        },
        isError = text.value.length > 10,
        visualTransformation = if (text.value.length > 10) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        maxLines = 1,
        readOnly = false,
        enabled = true,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            unfocusedTextColor = Color.Gray,
            disabledTextColor = Color.LightGray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.Gray,
            errorContainerColor = Color.Red,
            cursorColor = Color.Blue,
            errorCursorColor = Color.Red,
            selectionColors = TextSelectionColors(
                handleColor = Color.Blue,
                backgroundColor = Color.LightGray.copy(alpha = 0.4f)
            ),
            focusedIndicatorColor = Color.Green,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray,
            errorIndicatorColor = Color.Yellow,
            focusedLeadingIconColor = Color.Magenta,
            unfocusedLeadingIconColor = Color.Cyan,
            disabledLeadingIconColor = Color.Gray,
            errorLeadingIconColor = Color.Gray,
            focusedTrailingIconColor = Color.Magenta,
            unfocusedTrailingIconColor = Color.Cyan,
            disabledTrailingIconColor = Color.Gray,
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = Color.Magenta,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = Color.LightGray,
            errorLabelColor = Color.Red,
            focusedPlaceholderColor = Color.Magenta,
            unfocusedPlaceholderColor = Color.LightGray,
            disabledPlaceholderColor = Color.Gray,
            errorPlaceholderColor = Color.Red,
            focusedSupportingTextColor = Color.Green,
            unfocusedSupportingTextColor = Color.Gray,
            disabledSupportingTextColor = Color.LightGray,
            errorSupportingTextColor = Color.Red,
            focusedPrefixColor = Color.Blue,
            unfocusedPrefixColor = Color.Gray,
            disabledPrefixColor = Color.LightGray,
            errorPrefixColor = Color.Red,
            focusedSuffixColor = Color.Blue,
            unfocusedSuffixColor = Color.Gray,
            disabledSuffixColor = Color.LightGray,
            errorSuffixColor = Color.Red
        )
    )


}
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun EditableTextFieldPreview() {
    EditableTextField(
        index = 0,
        onValueChange = {},
    )
}
