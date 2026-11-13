package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.ContactDispatchRecord
import com.example.model.ContactMethod
import com.example.model.ServiceSpecialist
import com.example.model.UrgencyLevel
import com.example.ui.HomeCraftUiState
import com.example.ui.TradeViewModel
import com.example.ui.components.TradeHeroBanner
import com.example.ui.components.copyToClipboard
import com.example.ui.components.launchDialer
import com.example.ui.components.launchEmail
import com.example.ui.components.launchSms
import com.example.ui.theme.LocalTradeAccents

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ElectricianContactScreen(
    uiState: HomeCraftUiState,
    onSelectElectrician: (String) -> Unit,
    onOpenDetails: (ServiceSpecialist) -> Unit,
    onNameChange: (String) -> Unit,
    onNeedsChange: (String) -> Unit,
    onAppendQuickIssue: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSelectPreferredMethod: (ContactMethod) -> Unit,
    onSelectUrgency: (UrgencyLevel) -> Unit,
    onContactDetailChange: (String) -> Unit,
    onSubmitContact: () -> ContactDispatchRecord?,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalTradeAccents.current
    val context = LocalContext.current
    val form = uiState.contactForm
    val selectedElectrician = uiState.selectedElectrician

    val liveFormattedMessage = remember(
        selectedElectrician?.id,
        form.customerName,
        form.electricalNeeds,
        form.location,
        form.preferredMethod,
        form.urgency,
        form.customerContactDetail
    ) {
        TradeViewModel.formatElectricianMessage(
            electricianName = selectedElectrician?.name ?: "Electrician Team",
            licenseTag = selectedElectrician?.licenseTag ?: "ELC-PRO",
            customerName = form.customerName,
            electricalNeeds = form.electricalNeeds,
            location = form.location,
            preferredMethod = form.preferredMethod,
            urgency = form.urgency,
            contactDetail = form.customerContactDetail
        )
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth >= 820.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .testTag("electrician_contact_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TradeHeroBanner(
                partBadge = stringResource(R.string.part_three_label),
                title = stringResource(R.string.electrician_hero_title),
                subtitle = stringResource(R.string.electrician_hero_subtitle),
                accentColor = accents.electricianAccent,
                bannerRes = R.drawable.img_electrician_banner,
                icon = Icons.Outlined.Bolt
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "1. Select Fictional Electrician",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${uiState.electricians.size} on duty",
                        style = MaterialTheme.typography.labelMedium,
                        color = accents.electricianAccent
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.electricians.forEach { electrician ->
                        val isSelected = electrician.id == selectedElectrician?.id
                        ElectricianSelectorCard(
                            electrician = electrician,
                            isSelected = isSelected,
                            onSelect = { onSelectElectrician(electrician.id) },
                            onDetails = { onOpenDetails(electrician) },
                            onQuickCall = { launchDialer(context, electrician.phone, onShowMessage) }
                        )
                    }
                }
            }

            if (isWide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    ElectricianInputFormCard(
                        uiState = uiState,
                        onNameChange = onNameChange,
                        onNeedsChange = onNeedsChange,
                        onAppendQuickIssue = onAppendQuickIssue,
                        onLocationChange = onLocationChange,
                        onSelectPreferredMethod = onSelectPreferredMethod,
                        onSelectUrgency = onSelectUrgency,
                        onContactDetailChange = onContactDetailChange,
                        modifier = Modifier.weight(1.1f)
                    )
                    FormattedMessagePreviewCard(
                        selectedElectrician = selectedElectrician,
                        preferredMethod = form.preferredMethod,
                        formattedMessage = liveFormattedMessage,
                        onCopyMessage = {
                            copyToClipboard(context, "Electrician Contact Message", liveFormattedMessage)
                            onShowMessage("Formatted message copied to clipboard.")
                        },
                        onSendRequest = {
                            val record = onSubmitContact()
                            if (record != null) {
                                onShowMessage("Contact request ${record.id} formatted for ${record.electricianName}.")
                            }
                        },
                        onLaunchChannel = {
                            val target = selectedElectrician ?: return@FormattedMessagePreviewCard
                            when (form.preferredMethod) {
                                ContactMethod.CALL -> launchDialer(context, target.phone, onShowMessage)
                                ContactMethod.SMS -> launchSms(context, target.phone, liveFormattedMessage, onShowMessage)
                                ContactMethod.EMAIL -> launchEmail(
                                    context = context,
                                    email = target.email,
                                    subject = "Electrical Service Request - ${form.customerName.ifBlank { "HomeCraft User" }}",
                                    body = liveFormattedMessage,
                                    onStatus = onShowMessage
                                )
                            }
                        },
                        modifier = Modifier.weight(0.9f)
                    )
                }
            } else {
                ElectricianInputFormCard(
                    uiState = uiState,
                    onNameChange = onNameChange,
                    onNeedsChange = onNeedsChange,
                    onAppendQuickIssue = onAppendQuickIssue,
                    onLocationChange = onLocationChange,
                    onSelectPreferredMethod = onSelectPreferredMethod,
                    onSelectUrgency = onSelectUrgency,
                    onContactDetailChange = onContactDetailChange
                )

                FormattedMessagePreviewCard(
                    selectedElectrician = selectedElectrician,
                    preferredMethod = form.preferredMethod,
                    formattedMessage = liveFormattedMessage,
                    onCopyMessage = {
                        copyToClipboard(context, "Electrician Contact Message", liveFormattedMessage)
                        onShowMessage("Formatted message copied to clipboard.")
                    },
                    onSendRequest = {
                        val record = onSubmitContact()
                        if (record != null) {
                            onShowMessage("Contact request ${record.id} formatted for ${record.electricianName}.")
                        }
                    },
                    onLaunchChannel = {
                        val target = selectedElectrician ?: return@FormattedMessagePreviewCard
                        when (form.preferredMethod) {
                            ContactMethod.CALL -> launchDialer(context, target.phone, onShowMessage)
                            ContactMethod.SMS -> launchSms(context, target.phone, liveFormattedMessage, onShowMessage)
                            ContactMethod.EMAIL -> launchEmail(
                                context = context,
                                email = target.email,
                                subject = "Electrical Service Request - ${form.customerName.ifBlank { "HomeCraft User" }}",
                                body = liveFormattedMessage,
                                onStatus = onShowMessage
                            )
                        }
                    }
                )
            }

            if (uiState.sentContactRecords.isNotEmpty()) {
                Text(
                    text = "Sent Messages to Electricians (${uiState.sentContactRecords.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                uiState.sentContactRecords.forEach { record ->
                    SentContactRecordCard(
                        record = record,
                        onCopy = {
                            copyToClipboard(context, "Sent Message ${record.id}", record.formattedMessage)
                            onShowMessage("Copied ${record.id} message.")
                        },
                        onOpenSms = {
                            launchSms(context, record.electricianPhone, record.formattedMessage, onShowMessage)
                        },
                        onOpenEmail = {
                            launchEmail(
                                context,
                                record.electricianEmail,
                                "Electrical Service Request (${record.id})",
                                record.formattedMessage,
                                onShowMessage
                            )
                        },
                        onOpenCall = {
                            launchDialer(context, record.electricianPhone, onShowMessage)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ElectricianSelectorCard(
    electrician: ServiceSpecialist,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDetails: () -> Unit,
    onQuickCall: () -> Unit
) {
    val accents = LocalTradeAccents.current
    Card(
        onClick = onSelect,
        modifier = Modifier
            .width(248.dp)
            .testTag("select_electrician_${electrician.id}"),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) accents.electricianContainer.copy(alpha = 0.55f) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) accents.electricianAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isSelected) accents.electricianAccent else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = if (isSelected) "Selected" else electrician.licenseTag,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = accents.voltageGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${electrician.rating}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = electrician.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = null,
                        tint = accents.electricianAccent,
                        modifier = Modifier.size(15.dp)
                    )
                }
                Text(
                    text = electrician.primaryCategory,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                Text(
                    text = electrician.availability,
                    style = MaterialTheme.typography.labelSmall,
                    color = accents.electricianAccent,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${electrician.hourlyRateUsd}/hr",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    OutlinedButton(
                        onClick = onDetails,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Text("Bio", style = MaterialTheme.typography.labelSmall)
                    }
                    IconButton(
                        onClick = onQuickCall,
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Call,
                            contentDescription = "Call ${electrician.name}",
                            tint = accents.electricianAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ElectricianInputFormCard(
    uiState: HomeCraftUiState,
    onNameChange: (String) -> Unit,
    onNeedsChange: (String) -> Unit,
    onAppendQuickIssue: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSelectPreferredMethod: (ContactMethod) -> Unit,
    onSelectUrgency: (UrgencyLevel) -> Unit,
    onContactDetailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalTradeAccents.current
    val form = uiState.contactForm

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.7f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "2. Enter Your Electrical Needs",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = form.customerName,
                onValueChange = onNameChange,
                label = { Text("Your Name *") },
                placeholder = { Text("e.g., Alex Morgan") },
                leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                isError = form.nameError != null,
                supportingText = form.nameError?.let { err -> { Text(err) } },
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accents.electricianAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("customer_name_input")
            )

            OutlinedTextField(
                value = form.location,
                onValueChange = onLocationChange,
                label = { Text("Your Location / Address *") },
                placeholder = { Text("e.g., 42 Maple Ave, Apt 3B") },
                leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
                isError = form.locationError != null,
                supportingText = form.locationError?.let { err -> { Text(err) } },
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accents.electricianAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("location_input")
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Quick-Add Common Issues",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    uiState.quickElectricalIssues.forEach { issue ->
                        AssistChip(
                            onClick = { onAppendQuickIssue(issue) },
                            label = { Text(issue, style = MaterialTheme.typography.labelSmall) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }
            }

            OutlinedTextField(
                value = form.electricalNeeds,
                onValueChange = onNeedsChange,
                label = { Text("Description of Electrical Needs *") },
                placeholder = { Text("Describe tripping breakers, outlet issues, wiring, lighting install…") },
                isError = form.needsError != null,
                supportingText = form.needsError?.let { err -> { Text(err) } },
                minLines = 3,
                maxLines = 5,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accents.electricianAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("electrical_needs_input")
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Urgency Window",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UrgencyLevel.entries.forEach { level ->
                        val selected = form.urgency == level
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectUrgency(level) },
                            label = { Text(level.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accents.electricianContainer,
                                selectedLabelColor = accents.electricianOnContainer
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Preferred Contact Method *",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ContactMethod.entries.forEach { method ->
                        val selected = form.preferredMethod == method
                        val icon = when (method) {
                            ContactMethod.CALL -> Icons.Outlined.Phone
                            ContactMethod.SMS -> Icons.Outlined.Sms
                            ContactMethod.EMAIL -> Icons.Outlined.Email
                        }
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectPreferredMethod(method) },
                            leadingIcon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            label = { Text(method.displayName, maxLines = 1) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accents.electricianContainer,
                                selectedLabelColor = accents.electricianOnContainer
                            ),
                            modifier = Modifier.testTag("contact_method_${method.name.lowercase()}")
                        )
                    }
                }
            }

            OutlinedTextField(
                value = form.customerContactDetail,
                onValueChange = onContactDetailChange,
                label = {
                    val hint = when (form.preferredMethod) {
                        ContactMethod.CALL -> "Your Callback Phone (optional)"
                        ContactMethod.SMS -> "Your SMS Number (optional)"
                        ContactMethod.EMAIL -> "Your Email Address (optional)"
                    }
                    Text(hint)
                },
                placeholder = {
                    val ph = when (form.preferredMethod) {
                        ContactMethod.EMAIL -> "e.g., alex@example.com"
                        else -> "e.g., +1-555-0199"
                    }
                    Text(ph)
                },
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = accents.electricianAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("customer_contact_detail_input")
            )
        }
    }
}

@Composable
private fun FormattedMessagePreviewCard(
    selectedElectrician: ServiceSpecialist?,
    preferredMethod: ContactMethod,
    formattedMessage: String,
    onCopyMessage: () -> Unit,
    onSendRequest: () -> Unit,
    onLaunchChannel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalTradeAccents.current
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, accents.electricianAccent.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "3. Formatted Message Preview",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "To: ${selectedElectrician?.name ?: "Electrician"} (${selectedElectrician?.phone ?: ""})",
                        style = MaterialTheme.typography.bodySmall,
                        color = accents.electricianAccent
                    )
                }
                IconButton(
                    onClick = onCopyMessage,
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy formatted message",
                        tint = accents.electricianAccent
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            ) {
                Text(
                    text = formattedMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .testTag("formatted_message_preview")
                )
            }

            Button(
                onClick = onSendRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("send_electrician_contact_button"),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = accents.electricianAccent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.action_send_request))
            }

            val channelActionText = when (preferredMethod) {
                ContactMethod.CALL -> "Open Phone Dialer (${selectedElectrician?.phone ?: ""})"
                ContactMethod.SMS -> "Send via SMS App"
                ContactMethod.EMAIL -> "Send via Email App"
            }

            OutlinedButton(
                onClick = onLaunchChannel,
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("launch_preferred_channel_button"),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, accents.electricianAccent)
            ) {
                Text(
                    text = channelActionText,
                    color = accents.electricianAccent
                )
            }
        }
    }
}

@Composable
private fun SentContactRecordCard(
    record: ContactDispatchRecord,
    onCopy: () -> Unit,
    onOpenSms: () -> Unit,
    onOpenEmail: () -> Unit,
    onOpenCall: () -> Unit
) {
    val accents = LocalTradeAccents.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sent_record_${record.id}"),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, accents.availableGreen.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = accents.availableGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${record.id} • To ${record.electricianName}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = record.createdAtLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = record.formattedMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenCall,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text("Call", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = onOpenSms,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text("SMS", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = onOpenEmail,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text("Email", style = MaterialTheme.typography.labelMedium)
                }
                IconButton(onClick = onCopy) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy sent message",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
