package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false,
    text: String,
) {
    Button(
        onClick = onClick,
        shape = Shapes().large,
        modifier = modifier,
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(onClick = {}, text = "Button")
}
