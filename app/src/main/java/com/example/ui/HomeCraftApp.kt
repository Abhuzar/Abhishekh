package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Carpenter
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Carpenter
import androidx.compose.material.icons.outlined.HomeRepairService
import androidx.compose.material.icons.outlined.Plumbing
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.model.TradePart
import com.example.ui.components.SpecialistDetailBottomSheet
import com.example.ui.components.launchDialer
import com.example.ui.components.launchEmail
import com.example.ui.components.launchSms
import com.example.ui.screens.CarpenterScreen
import com.example.ui.screens.ElectricianContactScreen
import com.example.ui.screens.PlumberScreen
import com.example.ui.theme.LocalTradeAccents
import kotlinx.coroutines.launch

@Composable
fun HomeCraftApp(
    viewModel: TradeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: TradePart.PLUMBER.route
    val currentPart = TradePart.entries.find { it.route == currentRoute } ?: TradePart.PLUMBER

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val accents = LocalTradeAccents.current

    val showMessage: (String) -> Unit = { msg ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(msg)
        }
    }

    val navigateToPart: (TradePart) -> Unit = { part ->
        navController.navigate(part.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 680.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                HomeCraftTopHeader(
                    currentPart = currentPart,
                    onSelectPart = navigateToPart
                )
            },
            bottomBar = {
                if (!useNavigationRail) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 4.dp,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        TradePart.entries.forEach { part ->
                            val selected = currentPart == part
                            val (accent, container, onContainer) = partColors(part)
                            val selectedIcon = when (part) {
                                TradePart.PLUMBER -> Icons.Filled.Plumbing
                                TradePart.CARPENTER -> Icons.Filled.Carpenter
                                TradePart.ELECTRICIAN -> Icons.Filled.Bolt
                            }
                            val unselectedIcon = when (part) {
                                TradePart.PLUMBER -> Icons.Outlined.Plumbing
                                TradePart.CARPENTER -> Icons.Outlined.Carpenter
                                TradePart.ELECTRICIAN -> Icons.Outlined.Bolt
                            }

                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateToPart(part) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) selectedIcon else unselectedIcon,
                                        contentDescription = "${part.partBadge} ${part.label}"
                                    )
                                },
                                label = {
                                    Text(
                                        text = part.label,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = accent,
                                    selectedTextColor = accent,
                                    indicatorColor = container,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.testTag("nav_tab_${part.route}")
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        TradePart.entries.forEach { part ->
                            val selected = currentPart == part
                            val (accent, container, _) = partColors(part)
                            val icon = when (part) {
                                TradePart.PLUMBER -> if (selected) Icons.Filled.Plumbing else Icons.Outlined.Plumbing
                                TradePart.CARPENTER -> if (selected) Icons.Filled.Carpenter else Icons.Outlined.Carpenter
                                TradePart.ELECTRICIAN -> if (selected) Icons.Filled.Bolt else Icons.Outlined.Bolt
                            }
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToPart(part) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "${part.partBadge} ${part.label}"
                                    )
                                },
                                label = { Text(part.label) },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = accent,
                                    selectedTextColor = accent,
                                    indicatorColor = container
                                ),
                                modifier = Modifier.testTag("rail_tab_${part.route}")
                            )
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    NavHost(
                        navController = navController,
                        startDestination = TradePart.PLUMBER.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(TradePart.PLUMBER.route) {
                            PlumberScreen(
                                uiState = uiState,
                                onSearchChange = viewModel::updatePlumberSearch,
                                onCategorySelect = viewModel::selectPlumberCategory,
                                onToggleAvailable = viewModel::togglePlumberAvailableOnly,
                                onClearFilters = viewModel::clearPlumberFilters,
                                onOpenDetails = viewModel::openSpecialistDetails,
                                onShowMessage = showMessage
                            )
                        }
                        composable(TradePart.CARPENTER.route) {
                            CarpenterScreen(
                                uiState = uiState,
                                onSearchChange = viewModel::updateCarpenterSearch,
                                onCategorySelect = viewModel::selectCarpenterCategory,
                                onToggleAvailable = viewModel::toggleCarpenterAvailableOnly,
                                onClearFilters = viewModel::clearCarpenterFilters,
                                onOpenDetails = viewModel::openSpecialistDetails,
                                onShowMessage = showMessage
                            )
                        }
                        composable(TradePart.ELECTRICIAN.route) {
                            ElectricianContactScreen(
                                uiState = uiState,
                                onSelectElectrician = viewModel::selectElectrician,
                                onOpenDetails = viewModel::openSpecialistDetails,
                                onNameChange = viewModel::updateCustomerName,
                                onNeedsChange = viewModel::updateElectricalNeeds,
                                onAppendQuickIssue = viewModel::appendQuickElectricalIssue,
                                onLocationChange = viewModel::updateLocation,
                                onSelectPreferredMethod = viewModel::selectPreferredMethod,
                                onSelectUrgency = viewModel::selectUrgency,
                                onContactDetailChange = viewModel::updateCustomerContactDetail,
                                onSubmitContact = viewModel::submitElectricianContact,
                                onShowMessage = showMessage
                            )
                        }
                    }
                }
            }
        }
    }

    uiState.selectedSpecialistForDetails?.let { specialist ->
        SpecialistDetailBottomSheet(
            specialist = specialist,
            onDismiss = viewModel::closeSpecialistDetails,
            onCall = { pro -> launchDialer(context, pro.phone, showMessage) },
            onSms = { pro ->
                launchSms(
                    context = context,
                    phone = pro.phone,
                    body = "Hi ${pro.name}, I found your profile on HomeCraft and need assistance with ${pro.primaryCategory}.",
                    onStatus = showMessage
                )
            },
            onEmail = { pro ->
                launchEmail(
                    context = context,
                    email = pro.email,
                    subject = "HomeCraft Service Inquiry - ${pro.primaryCategory}",
                    body = "Hi ${pro.name},\n\nI would like to inquire about ${pro.primaryCategory} services.",
                    onStatus = showMessage
                )
            }
        )
    }
}

@Composable
private fun HomeCraftTopHeader(
    currentPart: TradePart,
    onSelectPart: (TradePart) -> Unit
) {
    val (accent, container, onContainer) = partColors(currentPart)
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = container,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.HomeRepairService,
                            contentDescription = "HomeCraft Logo",
                            tint = accent,
                            modifier = Modifier
                                .padding(7.dp)
                                .size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "HomeCraft",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "3-Part House Trade Directory",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = container,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = "${currentPart.partBadge} / 3",
                        style = MaterialTheme.typography.labelMedium,
                        color = onContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TradePart.entries.forEach { part ->
                    val selected = part == currentPart
                    val (pAccent, pContainer, pOnContainer) = partColors(part)
                    Surface(
                        onClick = { onSelectPart(part) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("top_part_pill_${part.route}"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (selected) pContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        border = BorderStroke(
                            width = if (selected) 1.5.dp else 1.dp,
                            color = if (selected) pAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = part.partBadge,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) pAccent else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = part.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selected) pOnContainer else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun partColors(part: TradePart): Triple<Color, Color, Color> {
    val accents = LocalTradeAccents.current
    return when (part) {
        TradePart.PLUMBER -> Triple(accents.plumberAccent, accents.plumberContainer, accents.plumberOnContainer)
        TradePart.CARPENTER -> Triple(accents.carpenterAccent, accents.carpenterContainer, accents.carpenterOnContainer)
        TradePart.ELECTRICIAN -> Triple(accents.electricianAccent, accents.electricianContainer, accents.electricianOnContainer)
    }
}
