package me.night_brainiac.mocket.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val lightColorScheme = lightColorScheme(
    primary = Color(0xFF9D3D5E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFD9E1),
    onPrimaryContainer = Color(0xFF3F001B),
    secondary = Color(0xFF74565E),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD9E1),
    onSecondaryContainer = Color(0xFF2B151B),
    tertiary = Color(0xFF7C5734),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDCC0),
    onTertiaryContainer = Color(0xFF2D1600),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A1B),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF201A1B),
    surfaceVariant = Color(0xFFF2DDE1),
    onSurfaceVariant = Color(0xFF514346),
    outline = Color(0xFF847376),
    inverseOnSurface = Color(0xFFFAEEEF),
    inverseSurface = Color(0xFF352F30),
    inversePrimary = Color(0xFFFFB1C5),
    surfaceTint = Color(0xFF9D3D5E),
    outlineVariant = Color(0xFFD6C2C5),
    scrim = Color(0xFF000000),
)

private val darkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB1C5),
    onPrimary = Color(0xFF610B30),
    primaryContainer = Color(0xFF7F2546),
    onPrimaryContainer = Color(0xFFFFD9E1),
    inversePrimary = Color(0xFF9D3D5E),
    secondary = Color(0xFFE3BDC5),
    onSecondary = Color(0xFF9D3D5E),
    secondaryContainer = Color(0xFF5B3F46),
    onSecondaryContainer = Color(0xFFFFD9E1),
    tertiary = Color(0xFFEEBD93),
    onTertiary = Color(0xFF472A0A),
    tertiaryContainer = Color(0xFF613F1F),
    onTertiaryContainer = Color(0xFFFFDCC0),
    background = Color(0xFF201A1B),
    onBackground = Color(0xFFECE0E1),
    surface = Color(0xFF201A1B),
    onSurface = Color(0xFFECE0E1),
    surfaceVariant = Color(0xFF514346),
    onSurfaceVariant = Color(0xFFD6C2C5),
    surfaceTint = Color(0xFFFFB1C5),
    inverseSurface = Color(0xFFECE0E1),
    inverseOnSurface = Color(0xFF201A1B),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFF9E8C90),
    outlineVariant = Color(0xFF514346),
    scrim = Color(0xFF000000),
)

@Composable
fun MocketTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDarkTheme) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
        }
        isDarkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}