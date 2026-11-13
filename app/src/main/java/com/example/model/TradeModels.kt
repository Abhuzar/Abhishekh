package com.example.model

enum class TradePart(val route: String, val partBadge: String, val label: String) {
    PLUMBER(
        route = "plumber",
        partBadge = "Part 1",
        label = "Plumber"
    ),
    CARPENTER(
        route = "carpenter",
        partBadge = "Part 2",
        label = "Carpenter"
    ),
    ELECTRICIAN(
        route = "electrician",
        partBadge = "Part 3",
        label = "Electrician"
    )
}

enum class ContactMethod(val displayName: String, val detailLabel: String) {
    CALL("Phone Call", "Call back on my phone"),
    SMS("Text / SMS", "Send SMS text message"),
    EMAIL("Email", "Reply via email")
}

enum class UrgencyLevel(val label: String, val window: String) {
    URGENT("Urgent", "Within 45 min"),
    TODAY("Today", "Same-day slot"),
    SCHEDULED("Flexible", "Next 1-3 days")
}

data class ServiceSpecialist(
    val id: String,
    val trade: TradePart,
    val name: String,
    val title: String,
    val primaryCategory: String,
    val rating: Double,
    val reviewCount: Int,
    val yearsExperience: Int,
    val hourlyRateUsd: Int,
    val visitFeeUsd: Int,
    val availability: String,
    val isAvailableNow: Boolean,
    val area: String,
    val licenseTag: String,
    val phone: String,
    val email: String,
    val bio: String,
    val houseServices: List<String>,
    val highlights: List<String>
)

data class ElectricalContactFormState(
    val selectedElectricianId: String = "elec-1",
    val customerName: String = "",
    val electricalNeeds: String = "",
    val location: String = "",
    val preferredMethod: ContactMethod = ContactMethod.CALL,
    val urgency: UrgencyLevel = UrgencyLevel.TODAY,
    val customerContactDetail: String = "",
    val nameError: String? = null,
    val needsError: String? = null,
    val locationError: String? = null
)

data class ContactDispatchRecord(
    val id: String,
    val electricianName: String,
    val electricianPhone: String,
    val electricianEmail: String,
    val customerName: String,
    val location: String,
    val preferredMethod: ContactMethod,
    val urgency: UrgencyLevel,
    val formattedMessage: String,
    val createdAtLabel: String
)
