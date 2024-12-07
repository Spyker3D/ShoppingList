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
import com.practicum.buyinglist.ui.theme.AppTypography
import com.practicum.buyinglist.ui.theme.black
import com.practicum.buyinglist.ui.theme.blackLightForCounter
import com.practicum.buyinglist.ui.theme.blue
import com.practicum.buyinglist.ui.theme.cyan
import com.practicum.buyinglist.ui.theme.grey
import com.practicum.buyinglist.ui.theme.lightBlue
import com.practicum.buyinglist.ui.theme.lightGrey
import com.practicum.buyinglist.ui.theme.lightOutline
import com.practicum.buyinglist.ui.theme.onSurface
import com.practicum.buyinglist.ui.theme.outlineVariant
import com.practicum.buyinglist.ui.theme.red
import com.practicum.buyinglist.ui.theme.white

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
        tertiaryContainer = white,
        onTertiaryContainer = blackLightForCounter,
        surfaceVariant = lightBlue,
        onBackground = blue,
        onError = red,
        errorContainer = red,
        outline = lightOutline,
    )

private val darkScheme =
    darkColorScheme(
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
        tertiaryContainer = white,
        onTertiaryContainer = blackLightForCounter,
        surfaceVariant = lightBlue,
        onBackground = blue,
        onError = red,
        errorContainer = red,
        outline = lightOutline,
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
    dynamicColor: Boolean = true,
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
