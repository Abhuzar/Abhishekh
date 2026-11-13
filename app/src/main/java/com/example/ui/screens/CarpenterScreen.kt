package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Carpenter
import androidx.compose.material.icons.outlined.Handyman
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.MaterialTheme
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
fun CarpenterScreen(
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
    val carpenters = uiState.filteredCarpenters

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth >= 680.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("carpenter_screen_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                TradeHeroBanner(
                    partBadge = stringResource(R.string.part_two_label),
                    title = stringResource(R.string.carpenter_hero_title),
                    subtitle = stringResource(R.string.carpenter_hero_subtitle),
                    accentColor = accents.carpenterAccent,
                    bannerRes = R.drawable.img_carpenter_banner,
                    icon = Icons.Outlined.Carpenter
                )
            }

            item {
                HouseWorkQuickGuideStrip(
                    title = "Residential Woodwork & Craft Estimates",
                    items = listOf(
                        "Door & Lock Fix" to "$52/hr",
                        "Custom Cabinets" to "$65/hr",
                        "Furniture Joinery" to "$58/hr"
                    ),
                    accentColor = accents.carpenterAccent,
                    containerColor = accents.carpenterContainer,
                    onContainerColor = accents.carpenterOnContainer
                )
            }

            item {
                SearchAndCategoryFilterBar(
                    searchQuery = uiState.carpenterSearchQuery,
                    onSearchChange = onSearchChange,
                    searchPlaceholder = stringResource(R.string.search_carpenter_hint),
                    searchTestTag = "carpenter_search_input",
                    categories = uiState.carpenterCategories,
                    selectedCategory = uiState.selectedCarpenterCategory,
                    onCategorySelect = onCategorySelect,
                    onlyAvailable = uiState.onlyAvailableCarpenters,
                    onToggleAvailable = onToggleAvailable,
                    accentColor = accents.carpenterAccent,
                    accentContainer = accents.carpenterContainer,
                    onAccentContainer = accents.carpenterOnContainer
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${carpenters.size} Skilled Carpenters",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Custom & Repair Work",
                        style = MaterialTheme.typography.labelMedium,
                        color = accents.carpenterAccent
                    )
                }
            }

            if (carpenters.isEmpty()) {
                item {
                    EmptyFilterState(
                        icon = Icons.Outlined.Handyman,
                        onClearFilters = onClearFilters
                    )
                }
            } else if (isWide) {
                val rows = carpenters.chunked(2)
                items(rows) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { carpenter ->
                            SpecialistCard(
                                specialist = carpenter,
                                accentColor = accents.carpenterAccent,
                                accentContainer = accents.carpenterContainer,
                                onAccentContainer = accents.carpenterOnContainer,
                                onViewDetails = { onOpenDetails(carpenter) },
                                onPrimaryAction = { launchDialer(context, carpenter.phone, onShowMessage) },
                                primaryActionLabel = "Call Carpenter",
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
                items(carpenters, key = { it.id }) { carpenter ->
                    SpecialistCard(
                        specialist = carpenter,
                        accentColor = accents.carpenterAccent,
                        accentContainer = accents.carpenterContainer,
                        onAccentContainer = accents.carpenterOnContainer,
                        onViewDetails = { onOpenDetails(carpenter) },
                        onPrimaryAction = { launchDialer(context, carpenter.phone, onShowMessage) },
                        primaryActionLabel = "Call Carpenter",
                        primaryActionIcon = Icons.Rounded.Call
                    )
                }
            }
        }
    }
}
