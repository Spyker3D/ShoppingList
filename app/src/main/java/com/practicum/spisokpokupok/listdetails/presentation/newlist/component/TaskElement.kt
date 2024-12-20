package com.practicum.spisokpokupok.listdetails.presentation.newlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.buyinglist.R.drawable.ic_arrow_right
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType

@Composable
fun TaskElement(
    onValueChange: (String) -> Unit,
    isRedacted: Boolean,
    name: String,
    errorMesage: String,
    isError: Boolean,
    quantity: String,
    quantityType: QuantityType,
    onElementClick: () -> Unit,
    modifier: Modifier,
    onClearClick: () -> Unit,
) {
    Column(
        modifier = modifier.height(72.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                modifier
                    .height(71.dp)
                    .fillMaxWidth()
                    .clickable { onElementClick() },
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                if (isRedacted) {
                    NewTaskEditableTextField(
                        value = name,
                        onValueChange = onValueChange,
                        modifier = Modifier.height(76.dp).fillMaxWidth(),
                        isError = isError,
                        errorMessage = errorMesage,
                        onClearClick = onClearClick,
                    )
                } else {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.height(72.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = name,
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

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSurface,
                            thickness = 1.dp,
                            modifier = Modifier,
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alpha(0.5f),
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TaskElementPreview() {
    TaskElement(
        name = "Огурцы",
        onElementClick = {},
        modifier = Modifier,
        quantity = "1",
        quantityType = QuantityType.PIECE,
        isRedacted = false,
        onValueChange = {},
        onClearClick = {},
        isError = false,
        errorMesage = "",
    )
}
