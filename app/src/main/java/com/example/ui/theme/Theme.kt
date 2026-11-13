package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class TradeAccentColors(
    val plumberAccent: Color,
    val plumberContainer: Color,
    val plumberOnContainer: Color,
    val carpenterAccent: Color,
    val carpenterContainer: Color,
    val carpenterOnContainer: Color,
    val electricianAccent: Color,
    val electricianContainer: Color,
    val electricianOnContainer: Color,
    val voltageGold: Color,
    val availableGreen: Color,
    val availableContainer: Color
)

private val LightTradeAccents = TradeAccentColors(
    plumberAccent = PlumberAccentLight,
    plumberContainer = PlumberContainerLight,
    plumberOnContainer = PlumberOnContainerLight,
    carpenterAccent = CarpenterAccentLight,
    carpenterContainer = CarpenterContainerLight,
    carpenterOnContainer = CarpenterOnContainerLight,
    electricianAccent = ElectricianAccentLight,
    electricianContainer = ElectricianContainerLight,
    electricianOnContainer = ElectricianOnContainerLight,
    voltageGold = VoltageGold,
    availableGreen = StatusAvailableGreen,
    availableContainer = StatusAvailableContainer
)

private val DarkTradeAccents = TradeAccentColors(
    plumberAccent = PlumberAccentDark,
    plumberContainer = PlumberContainerDark,
    plumberOnContainer = PlumberOnContainerDark,
    carpenterAccent = CarpenterAccentDark,
    carpenterContainer = CarpenterContainerDark,
    carpenterOnContainer = CarpenterOnContainerDark,
    electricianAccent = ElectricianAccentDark,
    electricianContainer = ElectricianContainerDark,
    electricianOnContainer = ElectricianOnContainerDark,
    voltageGold = VoltageGold,
    availableGreen = Color(0xFF4ADE80),
    availableContainer = Color(0xFF14532D)
)

val LocalTradeAccents = staticCompositionLocalOf { LightTradeAccents }

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimaryDark,
    onPrimary = BrandOnPrimaryDark,
    primaryContainer = BrandPrimaryContainerDark,
    onPrimaryContainer = BrandOnPrimaryContainerDark,
    secondary = CarpenterAccentDark,
    tertiary = ElectricianAccentDark,
    background = DarkWorkshopCanvas,
    onBackground = CrispOffWhiteInk,
    surface = DarkCardSurface,
    onSurface = CrispOffWhiteInk,
    surfaceVariant = DarkElevatedSurface,
    onSurfaceVariant = MutedCoolGrayInk,
    outline = DarkOutlineSlate,
    error = Color(0xFFF87171)
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimaryLight,
    onPrimary = BrandOnPrimaryLight,
    primaryContainer = BrandPrimaryContainerLight,
    onPrimaryContainer = BrandOnPrimaryContainerLight,
    secondary = CarpenterAccentLight,
    tertiary = ElectricianAccentLight,
    background = AlabasterCanvas,
    onBackground = DeepCharcoalInk,
    surface = WarmPaperSurface,
    onSurface = DeepCharcoalInk,
    surfaceVariant = SoftStoneSurface,
    onSurfaceVariant = MutedSlateInk,
    outline = OutlineStone,
    error = ErrorCrimson
)

val HomeCraftShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun HomeCraftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val tradeAccents = if (darkTheme) DarkTradeAccents else LightTradeAccents

    CompositionLocalProvider(LocalTradeAccents provides tradeAccents) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = HomeCraftTypography,
            shapes = HomeCraftShapes,
            content = content
        )
    }
}
