package com.practicum.spisokpokupok.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
            .size(56.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}