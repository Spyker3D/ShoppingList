package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

@Composable
fun TitleEditableTextField(
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
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
            modifier =
                modifier
                    .height(76.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (!isRedacted && value.isEmpty()) {
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
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier =
                        Modifier.clickable {
                            onClearClick()
                        },
                )
            } else {
                val textState = remember { mutableStateOf(TextFieldValue(value)) }
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                DisposableEffect(Unit) {
                    textState.value =
                        textState.value.copy(
                            selection = TextRange(textState.value.text.length),
                        )
                    onDispose { }
                }
                BasicTextField(
                    value = textState.value,
                    onValueChange = {
                        textState.value = it
                        onValueChange(it.text)
                    },
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
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Done,
                        ),
                    keyboardActions = keyboardActions,
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp)
                            .focusRequester(focusRequester),
                    singleLine = true,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier =
                        Modifier.clickable {
                            textState.value = TextFieldValue("")
                            onClearClick()
                        },
                )
            }
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
    onClearClick: () -> Unit,
    isRedacted: Boolean,
    quantity: String,
    quantityType: QuantityType,
) {
    if (isRedacted) {
        Column {
            val textState = remember { mutableStateOf(TextFieldValue(value)) }
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            DisposableEffect(Unit) {
                textState.value =
                    textState.value.copy(
                        selection = TextRange(textState.value.text.length),
                    )
                onDispose { }
            }
            Row(
                modifier =
                    modifier
                        .height(if (isError) 56.dp else 76.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BasicTextField(
                    value = textState.value,
                    onValueChange = {
                        onValueChange(it.text)
                        textState.value = it
                    },
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
                        Modifier
                            .padding(horizontal = 16.dp)
                            .focusRequester(focusRequester),
                    singleLine = true,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier =
                        Modifier
                            .clickable {
                                onClearClick()
                                textState.value = TextFieldValue("")
                            },
                )
            }
            if (isError) {
                ShowError(
                    isError = isError,
                    errorMessage = errorMessage,
                    modifier = Modifier.padding(end = 16.dp),
                )
            } else {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    } else {
        Column {
            Row(
                modifier = modifier.height(if (isError) 56.dp else 72.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.height(72.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = value,
                        style =
                            TextStyle(
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        modifier = Modifier.alpha(0.5f).padding(horizontal = 16.dp),
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = quantity,
                            style =
                                TextStyle(
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                ),
                        )
                        Text(
                            text = quantityType.abbreviation,
                            style =
                                TextStyle(
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                ),
                            modifier =
                                Modifier.padding(
                                    start = 4.dp,
                                ),
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 16.dp),
                )
            }

            if (isError) {
                ShowError(
                    isError = isError,
                    errorMessage = errorMessage,
                    modifier = Modifier.padding(end = 16.dp),
                )
            } else {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun ShowError(
    isError: Boolean,
    errorMessage: String,
    modifier: Modifier,
) {
    if (isError) {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.error,
            )
            Text(
                text = errorMessage,
                modifier = modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    } else {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
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
        onClearClick = {},
        keyboardActions = KeyboardActions.Default,
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
        isError = false,
        onClearClick = {},
        keyboardActions = KeyboardActions.Default,
        isRedacted = false,
        quantity = "1",
        quantityType = QuantityType.PIECE,
    )
}
