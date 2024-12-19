package com.practicum.spisokpokupok.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightScheme =
    lightColorScheme(
        primary = cyan,
        onPrimary = white,
        surface = white,
        onSurface = onSurface,
        onSurfaceVariant = grey,
        onTertiary = black,
        outlineVariant = outlineVariant,
        secondary = lightGrey,
        onSecondary = white,
        secondaryContainer = lightBlue,
        onSecondaryContainer = white,
        tertiaryContainer = white, // контейнер выбранной единицы измерения в счетчике
        onTertiaryContainer = blackLightForCounter, // для выбранной единицы измерения в счетчике
        surfaceVariant = lightBlue,
        onBackground = blue, // для стрелки вниз "создать первый список" при отсутствии списков
        onError = red,
        errorContainer = red,
        outline = lightOutline, // граница для счетчика количества
        inversePrimary = cyan, // для кнопки "+ Добавить продукт", кнопок + и < > на главном экране со списками
        surfaceContainer = cyan // для иконки удаления при свайпе
    )

private val darkScheme =
    darkColorScheme(
        primary = white,
        onPrimary = cyan,
        surface = blackDarkTheme,
        onSurface = white,
        onSurfaceVariant = darkWhite,
        onTertiary = white,
        outlineVariant = grey,
        secondary = lightGrey,  // ok
        onSecondary = greyDark,
        secondaryContainer = lightBlue, // ok
        onSecondaryContainer = white,  // ok
        tertiaryContainer = white, // контейнер выбранной единицы измерения в счетчике
        onTertiaryContainer = black, // ok
        surfaceVariant = blueDark,
        onBackground = blue, // для стрелки вниз "создать первый список" при отсутствии списков
        onError = red, // ok
        errorContainer = red,  // ok
        outline = greyStrokeDark, // граница для счетчика количества
        inversePrimary = blue, // для кнопки "+ Добавить продукт", кнопок + и < > на главном экране со списками
        surfaceContainer = cyan // для иконки удаления при свайпе
    )

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecified_scheme =
    ColorFamily(
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
    )

@Composable
fun ToDoListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content:
        @Composable()
        () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> darkScheme
            else -> lightScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}
