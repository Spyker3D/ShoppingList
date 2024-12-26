/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.practicum.spisokpokupok.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopAppBar(
    onBack: () -> Unit,
    onSort: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.menu_back))
            }
        },
        actions = {
            IconButton(onClick = onSort) {
                Icon(
                    painter = painterResource(R.drawable.sori_ic),
                    stringResource(id = R.string.menu_sort_task),
                )
            }
        },
    )
}

@Preview
@Composable
private fun TaskDetailTopAppBarPreview() {
    ToDoListTheme {
        Surface {
            TaskDetailTopAppBar({ }, { }, "Title")
        }
    }
}
