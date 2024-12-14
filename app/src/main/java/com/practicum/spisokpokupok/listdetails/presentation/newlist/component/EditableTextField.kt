package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.buyinglist.R

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
        modifier =
            Modifier
                .fillMaxWidth(),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Label") },
        placeholder = { Text("Enter text") },
        trailingIcon = {
            IconButton(onClick = { text.value = "" }) {
                Icon(
                    painter =
                        painterResource(id = R.drawable.ic_close),
                    contentDescription = "Clear",
                )
            }
        },
        isError = text.value.length > 10,
        visualTransformation = if (text.value.length > 10) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions =
            KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
        singleLine = true,
        maxLines = 1,
        readOnly = false,
        enabled = true,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
        colors =
            TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
            ),
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
