package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R

@Composable
fun TitleEditableTextField(
    onValueChange: (String) -> Unit,
    topLabel: String,
    isError: Boolean,
    errorMessage: String,
    isRedacted: Boolean,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column {
        Row(
            modifier = modifier.height(76.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (!isRedacted) {
                Column(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = topLabel,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else {
                BasicTextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    textStyle =
                        if (isError) {
                            TextStyle(
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.error,
                            )
                        } else {
                            TextStyle(
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        },
                    keyboardActions = keyboardActions,
                    modifier =
                        Modifier.padding(horizontal = 16.dp),
                    singleLine = true,
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        if (isError) {
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.error,
            )
        } else {
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = errorMessage,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun NewTaskEditableTextField(
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    value: String,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column {
        Row(
            modifier = modifier.height(if (isError) 76.dp else 56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                textStyle =
                    if (isError) {
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.error,
                        )
                    } else {
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                keyboardActions = keyboardActions,
                modifier =
                    Modifier.padding(horizontal = 16.dp),
                singleLine = true,
            )
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        if (isError) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.error,
            )
        } else {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        if (isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun EditableTextFieldPreview() {
    TitleEditableTextField(
        onValueChange = {},
        value = "",
        modifier = Modifier.fillMaxWidth(),
        errorMessage = "ffsdfadsfasdf",
        label = "Название",
        topLabel = "Введите название списка",
        isRedacted = false,
        isError = false,
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun NewTaskEditableTextFieldPreview() {
    NewTaskEditableTextField(
        onValueChange = {},
        value = "dfgsdfg",
        modifier = Modifier.fillMaxWidth(),
        errorMessage = "ffsdfadsfasdf",
        isError = true,
    )
}
