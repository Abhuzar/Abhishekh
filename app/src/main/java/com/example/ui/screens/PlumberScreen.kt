package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Plumbing
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.ServiceSpecialist
import com.example.ui.HomeCraftUiState
import com.example.ui.components.EmptyFilterState
import com.example.ui.components.SearchAndCategoryFilterBar
import com.example.ui.components.SpecialistCard
import com.example.ui.components.TradeHeroBanner
import com.example.ui.components.launchDialer
import com.example.ui.theme.LocalTradeAccents

@Composable
fun PlumberScreen(
    uiState: HomeCraftUiState,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onToggleAvailable: () -> Unit,
    onClearFilters: () -> Unit,
    onOpenDetails: (ServiceSpecialist) -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalTradeAccents.current
    val context = LocalContext.current
    val plumbers = uiState.filteredPlumbers

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth >= 680.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("plumber_screen_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                TradeHeroBanner(
                    partBadge = stringResource(R.string.part_one_label),
                    title = stringResource(R.string.plumber_hero_title),
                    subtitle = stringResource(R.string.plumber_hero_subtitle),
                    accentColor = accents.plumberAccent,
                    bannerRes = R.drawable.img_plumber_banner,
                    icon = Icons.Outlined.Plumbing
                )
            }

            item {
                HouseWorkQuickGuideStrip(
                    title = "Common House Plumbing Rates",
                    items = listOf(
                        "Tap & Pipe Leak" to "$50–$75",
                        "Drain Clearing" to "$50+",
                        "Water Heater" to "$65+"
                    ),
                    accentColor = accents.plumberAccent,
                    containerColor = accents.plumberContainer,
                    onContainerColor = accents.plumberOnContainer
                )
            }

            item {
                SearchAndCategoryFilterBar(
                    searchQuery = uiState.plumberSearchQuery,
                    onSearchChange = onSearchChange,
                    searchPlaceholder = stringResource(R.string.search_plumber_hint),
                    searchTestTag = "plumber_search_input",
                    categories = uiState.plumberCategories,
                    selectedCategory = uiState.selectedPlumberCategory,
                    onCategorySelect = onCategorySelect,
                    onlyAvailable = uiState.onlyAvailablePlumbers,
                    onToggleAvailable = onToggleAvailable,
                    accentColor = accents.plumberAccent,
                    accentContainer = accents.plumberContainer,
                    onAccentContainer = accents.plumberOnContainer
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${plumbers.size} House Plumbers Available",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Verified Home Techs",
                        style = MaterialTheme.typography.labelMedium,
                        color = accents.plumberAccent
                    )
                }
            }

            if (plumbers.isEmpty()) {
                item {
                    EmptyFilterState(
                        icon = Icons.Outlined.WaterDrop,
                        onClearFilters = onClearFilters
                    )
                }
            } else if (isWide) {
                val rows = plumbers.chunked(2)
                items(rows) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { plumber ->
                            SpecialistCard(
                                specialist = plumber,
                                accentColor = accents.plumberAccent,
                                accentContainer = accents.plumberContainer,
                                onAccentContainer = accents.plumberOnContainer,
                                onViewDetails = { onOpenDetails(plumber) },
                                onPrimaryAction = { launchDialer(context, plumber.phone, onShowMessage) },
                                primaryActionLabel = "Call Plumber",
                                primaryActionIcon = Icons.Rounded.Call,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                            SpacerPlaceholder(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                items(plumbers, key = { it.id }) { plumber ->
                    SpecialistCard(
                        specialist = plumber,
                        accentColor = accents.plumberAccent,
                        accentContainer = accents.plumberContainer,
                        onAccentContainer = accents.plumberOnContainer,
                        onViewDetails = { onOpenDetails(plumber) },
                        onPrimaryAction = { launchDialer(context, plumber.phone, onShowMessage) },
                        primaryActionLabel = "Call Plumber",
                        primaryActionIcon = Icons.Rounded.Call
                    )
                }
            }
        }
    }
}

@Composable
fun HouseWorkQuickGuideStrip(
    title: String,
    items: List<Pair<String, String>>,
    accentColor: androidx.compose.ui.graphics.Color,
    containerColor: androidx.compose.ui.graphics.Color,
    onContainerColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = accentColor
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { (work, price) ->
                    Surface(
                        modifier = Modifier.weight(1f),
                        color = containerColor.copy(alpha = 0.65f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = work,
                                style = MaterialTheme.typography.labelSmall,
                                color = onContainerColor,
                                maxLines = 1
                            )
                            Text(
                                text = price,
                                style = MaterialTheme.typography.titleSmall,
                                color = onContainerColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpacerPlaceholder(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {}
}
