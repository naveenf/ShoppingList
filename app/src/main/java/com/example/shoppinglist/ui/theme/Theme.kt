package com.example.shoppinglist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class AppTheme {
    MODERN_LIGHT,
    MODERN_DARK,
    PAPER,
    BOTANIC
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondary,
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    error = Error,
    tertiary = Tertiary
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    error = Error,
    tertiary = Tertiary
)

private val PaperColorScheme = lightColorScheme(
    primary = PaperPrimary,
    onPrimary = PaperOnPrimary,
    secondary = PaperSecondary,
    onSecondary = PaperOnSecondary,
    background = PaperBackground,
    onBackground = PaperOnBackground,
    surface = PaperSurface,
    onSurface = PaperOnSurface,
    error = PaperError,
    tertiary = PaperTertiary
)

private val BotanicColorScheme = lightColorScheme(
    primary = BotanicPrimary,
    onPrimary = BotanicOnPrimary,
    secondary = BotanicSecondary,
    onSecondary = BotanicOnSecondary,
    background = BotanicBackground,
    onBackground = BotanicOnBackground,
    surface = BotanicSurface,
    onSurface = BotanicOnSurface,
    error = BotanicError,
    tertiary = BotanicTertiary
)

@Composable
fun ShoppingListTheme(
    theme: AppTheme = AppTheme.MODERN_LIGHT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        AppTheme.MODERN_LIGHT -> LightColorScheme
        AppTheme.MODERN_DARK -> DarkColorScheme
        AppTheme.PAPER -> PaperColorScheme
        AppTheme.BOTANIC -> BotanicColorScheme
    }
    
    val typography = when (theme) {
        AppTheme.PAPER -> PaperTypography
        AppTheme.BOTANIC -> BotanicTypography
        else -> Typography
    }

    val themedContent: @Composable () -> Unit = when (theme) {
        AppTheme.PAPER -> {
            {
                PaperBackground {
                    content()
                }
            }
        }
        AppTheme.BOTANIC -> {
            {
                BotanicBackground {
                    content()
                }
            }
        }
        else -> content
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = themedContent
    )
}
