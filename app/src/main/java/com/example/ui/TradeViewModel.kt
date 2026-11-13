package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.TradeDirectoryRepository
import com.example.model.ContactDispatchRecord
import com.example.model.ContactMethod
import com.example.model.ElectricalContactFormState
import com.example.model.ServiceSpecialist
import com.example.model.TradePart
import com.example.model.UrgencyLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HomeCraftUiState(
    val plumbers: List<ServiceSpecialist> = emptyList(),
    val carpenters: List<ServiceSpecialist> = emptyList(),
    val electricians: List<ServiceSpecialist> = emptyList(),
    val plumberCategories: List<String> = emptyList(),
    val carpenterCategories: List<String> = emptyList(),
    val quickElectricalIssues: List<String> = emptyList(),
    val plumberSearchQuery: String = "",
    val selectedPlumberCategory: String = "All",
    val onlyAvailablePlumbers: Boolean = false,
    val carpenterSearchQuery: String = "",
    val selectedCarpenterCategory: String = "All",
    val onlyAvailableCarpenters: Boolean = false,
    val selectedSpecialistForDetails: ServiceSpecialist? = null,
    val contactForm: ElectricalContactFormState = ElectricalContactFormState(),
    val sentContactRecords: List<ContactDispatchRecord> = emptyList()
) {
    val filteredPlumbers: List<ServiceSpecialist>
        get() = plumbers.filter { pro ->
            val matchesCategory = selectedPlumberCategory == "All" || pro.primaryCategory == selectedPlumberCategory
            val matchesAvail = !onlyAvailablePlumbers || pro.isAvailableNow
            val q = plumberSearchQuery.trim().lowercase()
            val matchesQuery = q.isEmpty() ||
                pro.name.lowercase().contains(q) ||
                pro.title.lowercase().contains(q) ||
                pro.primaryCategory.lowercase().contains(q) ||
                pro.houseServices.any { it.lowercase().contains(q) }
            matchesCategory && matchesAvail && matchesQuery
        }

    val filteredCarpenters: List<ServiceSpecialist>
        get() = carpenters.filter { pro ->
            val matchesCategory = selectedCarpenterCategory == "All" || pro.primaryCategory == selectedCarpenterCategory
            val matchesAvail = !onlyAvailableCarpenters || pro.isAvailableNow
            val q = carpenterSearchQuery.trim().lowercase()
            val matchesQuery = q.isEmpty() ||
                pro.name.lowercase().contains(q) ||
                pro.title.lowercase().contains(q) ||
                pro.primaryCategory.lowercase().contains(q) ||
                pro.houseServices.any { it.lowercase().contains(q) }
            matchesCategory && matchesAvail && matchesQuery
        }

    val selectedElectrician: ServiceSpecialist?
        get() = electricians.find { it.id == contactForm.selectedElectricianId } ?: electricians.firstOrNull()
}

class TradeViewModel(
    private val repository: TradeDirectoryRepository = TradeDirectoryRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeCraftUiState(
            plumbers = repository.getPlumbers(),
            carpenters = repository.getCarpenters(),
            electricians = repository.getElectricians(),
            plumberCategories = repository.plumberCategories,
            carpenterCategories = repository.carpenterCategories,
            quickElectricalIssues = repository.quickElectricalIssues
        )
    )
    val uiState: StateFlow<HomeCraftUiState> = _uiState.asStateFlow()

    fun updatePlumberSearch(query: String) {
        _uiState.update { it.copy(plumberSearchQuery = query) }
    }

    fun selectPlumberCategory(category: String) {
        _uiState.update { it.copy(selectedPlumberCategory = category) }
    }

    fun togglePlumberAvailableOnly() {
        _uiState.update { it.copy(onlyAvailablePlumbers = !it.onlyAvailablePlumbers) }
    }

    fun clearPlumberFilters() {
        _uiState.update {
            it.copy(
                plumberSearchQuery = "",
                selectedPlumberCategory = "All",
                onlyAvailablePlumbers = false
            )
        }
    }

    fun updateCarpenterSearch(query: String) {
        _uiState.update { it.copy(carpenterSearchQuery = query) }
    }

    fun selectCarpenterCategory(category: String) {
        _uiState.update { it.copy(selectedCarpenterCategory = category) }
    }

    fun toggleCarpenterAvailableOnly() {
        _uiState.update { it.copy(onlyAvailableCarpenters = !it.onlyAvailableCarpenters) }
    }

    fun clearCarpenterFilters() {
        _uiState.update {
            it.copy(
                carpenterSearchQuery = "",
                selectedCarpenterCategory = "All",
                onlyAvailableCarpenters = false
            )
        }
    }

    fun openSpecialistDetails(specialist: ServiceSpecialist) {
        _uiState.update { it.copy(selectedSpecialistForDetails = specialist) }
    }

    fun closeSpecialistDetails() {
        _uiState.update { it.copy(selectedSpecialistForDetails = null) }
    }

    fun selectElectrician(electricianId: String) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(selectedElectricianId = electricianId)
            )
        }
    }

    fun updateCustomerName(name: String) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(
                    customerName = name,
                    nameError = null
                )
            )
        }
    }

    fun updateElectricalNeeds(needs: String) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(
                    electricalNeeds = needs,
                    needsError = null
                )
            )
        }
    }

    fun appendQuickElectricalIssue(issue: String) {
        _uiState.update { state ->
            val current = state.contactForm.electricalNeeds.trim()
            val updated = when {
                current.isEmpty() -> issue
                current.contains(issue, ignoreCase = true) -> current
                else -> "$current; $issue"
            }
            state.copy(
                contactForm = state.contactForm.copy(
                    electricalNeeds = updated,
                    needsError = null
                )
            )
        }
    }

    fun updateLocation(location: String) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(
                    location = location,
                    locationError = null
                )
            )
        }
    }

    fun selectPreferredMethod(method: ContactMethod) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(preferredMethod = method)
            )
        }
    }

    fun selectUrgency(urgency: UrgencyLevel) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(urgency = urgency)
            )
        }
    }

    fun updateCustomerContactDetail(detail: String) {
        _uiState.update { state ->
            state.copy(
                contactForm = state.contactForm.copy(customerContactDetail = detail)
            )
        }
    }

    fun buildFormattedMessage(): String {
        val state = _uiState.value
        val electrician = state.selectedElectrician
        return formatElectricianMessage(
            electricianName = electrician?.name ?: "Electrician Team",
            licenseTag = electrician?.licenseTag ?: "ELC-PRO",
            customerName = state.contactForm.customerName,
            electricalNeeds = state.contactForm.electricalNeeds,
            location = state.contactForm.location,
            preferredMethod = state.contactForm.preferredMethod,
            urgency = state.contactForm.urgency,
            contactDetail = state.contactForm.customerContactDetail
        )
    }

    fun submitElectricianContact(): ContactDispatchRecord? {
        val state = _uiState.value
        val form = state.contactForm
        val trimmedName = form.customerName.trim()
        val trimmedNeeds = form.electricalNeeds.trim()
        val trimmedLocation = form.location.trim()

        val nameErr = if (trimmedName.isEmpty()) "Please enter your name" else null
        val needsErr = if (trimmedNeeds.isEmpty()) "Please describe your electrical issue" else null
        val locErr = if (trimmedLocation.isEmpty()) "Please enter your location or address" else null

        if (nameErr != null || needsErr != null || locErr != null) {
            _uiState.update {
                it.copy(
                    contactForm = it.contactForm.copy(
                        nameError = nameErr,
                        needsError = needsErr,
                        locationError = locErr
                    )
                )
            }
            return null
        }

        val electrician = state.selectedElectrician ?: return null
        val formatted = buildFormattedMessage()
        val timestamp = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        val record = ContactDispatchRecord(
            id = "REQ-${100 + state.sentContactRecords.size + 1}",
            electricianName = electrician.name,
            electricianPhone = electrician.phone,
            electricianEmail = electrician.email,
            customerName = trimmedName,
            location = trimmedLocation,
            preferredMethod = form.preferredMethod,
            urgency = form.urgency,
            formattedMessage = formatted,
            createdAtLabel = "Sent at $timestamp"
        )

        _uiState.update {
            it.copy(
                sentContactRecords = listOf(record) + it.sentContactRecords
            )
        }
        return record
    }

    companion object {
        fun formatElectricianMessage(
            electricianName: String,
            licenseTag: String,
            customerName: String,
            electricalNeeds: String,
            location: String,
            preferredMethod: ContactMethod,
            urgency: UrgencyLevel,
            contactDetail: String
        ): String {
            val safeName = customerName.trim().ifEmpty { "[Your Name]" }
            val safeLocation = location.trim().ifEmpty { "[Your Location]" }
            val safeNeeds = electricalNeeds.trim().ifEmpty { "[Description of electrical needs]" }
            val detailLine = contactDetail.trim().takeIf { it.isNotEmpty() }?.let { " ($it)" } ?: ""

            return buildString {
                appendLine("Hello $electricianName ($licenseTag),")
                appendLine()
                appendLine("Customer: $safeName")
                appendLine("Location: $safeLocation")
                appendLine("Urgency: ${urgency.label} (${urgency.window})")
                appendLine("Electrical Needs: $safeNeeds")
                append("Preferred Contact: ${preferredMethod.displayName}$detailLine")
            }
        }
    }
}
