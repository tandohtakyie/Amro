package aim.high.amro.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AmroDarkColorScheme = darkColorScheme(
    primary = AmroPrimary,
    secondary = AmroSecondary,
    tertiary = AmroAccent,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnBackground,
    onSecondary = DarkOnBackground,
    onTertiary = DarkOnBackground,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface
)

private val AmroLightColorScheme = lightColorScheme(
    primary = AmroPrimary,
    secondary = AmroSecondary,
    tertiary = AmroAccent,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnBackground,
    onSecondary = LightOnBackground,
    onTertiary = LightOnBackground,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface
)

@Composable
fun AmroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to prioritize brand identity
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> AmroDarkColorScheme
        else -> AmroLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AmroTypography,
        content = content
    )
}
