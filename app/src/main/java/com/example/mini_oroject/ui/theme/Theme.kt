package com.example.mini_oroject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
//"#0f7173","#daf4f0","#4c3f54","#8e7ea8","#efd6ac"
private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    secondary = DarkGrey,
    tertiary = LightGrey,
    onPrimary = Beige, // Lighter color for contrast on dark background
    onSecondary = LightGreen, // Lighter color for contrast on dark secondary
    surface = DarkGrey, // Darker color for surface in dark theme
    onSurface = Beige, // Lighter color for contrast on dark surface
    background =  DarkGrey // Consistent dark background
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    secondary = Beige,
    tertiary = LightGrey,
    onPrimary = DarkGreen, // Darker color for contrast on light background
    onSecondary = DarkGrey, // Darker color for contrast on light secondary
    surface = Beige, // Lighter color for surface in light theme
    onSurface = DarkGreen, // Darker color for contrast on light surface
    background = Beige // Consistent light background
)


@Composable
fun Mini_orojectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}