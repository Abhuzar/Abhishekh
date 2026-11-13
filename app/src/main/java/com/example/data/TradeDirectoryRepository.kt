package com.example.data

import com.example.model.ServiceSpecialist
import com.example.model.TradePart

class TradeDirectoryRepository {

    fun getPlumbers(): List<ServiceSpecialist> = listOf(
        ServiceSpecialist(
            id = "plumb-1",
            trade = TradePart.PLUMBER,
            name = "Arjun Mehta",
            title = "Master House Plumber",
            primaryCategory = "Leak & Pipe Repair",
            rating = 4.9,
            reviewCount = 148,
            yearsExperience = 12,
            hourlyRateUsd = 55,
            visitFeeUsd = 20,
            availability = "Available today • 25 min ETA",
            isAvailableNow = true,
            area = "Oakwood & Central District",
            licenseTag = "PLB-4082",
            phone = "+1-555-0142",
            email = "arjun.plumbing@homecraft.local",
            bio = "Specializes in fast residential leak detection, concealed copper/PEX pipe repair, and bathroom pressure balancing without unnecessary wall damage.",
            houseServices = listOf(
                "Burst & dripping pipe repair",
                "Under-sink P-trap & mixer tap install",
                "Bathroom valve & shower cartridge fix",
                "Wall moisture & pressure leak check"
            ),
            highlights = listOf("90-day workmanship warranty", "Pressure gauge diagnostics", "Clean drop-cloth setup")
        ),
        ServiceSpecialist(
            id = "plumb-2",
            trade = TradePart.PLUMBER,
            name = "Elena Rostova",
            title = "Kitchen & Bath Plumbing Tech",
            primaryCategory = "Kitchen & Bath",
            rating = 4.8,
            reviewCount = 112,
            yearsExperience = 9,
            hourlyRateUsd = 60,
            visitFeeUsd = 25,
            availability = "Available today • 40 min ETA",
            isAvailableNow = true,
            area = "Riverside & Westside",
            licenseTag = "PLB-5190",
            phone = "+1-555-0168",
            email = "elena.bath@homecraft.local",
            bio = "Detail-oriented residential plumber for full kitchen sink hookups, dishwasher lines, vanity upgrades, and modern concealed toilet cisterns.",
            houseServices = listOf(
                "Kitchen sink, disposal & RO line setup",
                "Toilet flush tank & seal replacement",
                "Washer & dishwasher inlet plumbing",
                "Silicone waterproofing & trap alignment"
            ),
            highlights = listOf("Fixture-safe soft-jaw tools", "Same-day spare valves", "Upfront flat quotes")
        ),
        ServiceSpecialist(
            id = "plumb-3",
            trade = TradePart.PLUMBER,
            name = "Daniel Brooks",
            title = "Drain & Sewer Specialist",
            primaryCategory = "Drain Cleaning",
            rating = 4.7,
            reviewCount = 94,
            yearsExperience = 11,
            hourlyRateUsd = 50,
            visitFeeUsd = 15,
            availability = "Next slot 2:30 PM",
            isAvailableNow = false,
            area = "North Hills & Maple Park",
            licenseTag = "PLB-3314",
            phone = "+1-555-0174",
            email = "daniel.drains@homecraft.local",
            bio = "Equipped with compact drain augers and camera scopes to clear stubborn kitchen grease clogs, floor traps, and main house sewer lines.",
            houseServices = listOf(
                "Hydro-jet & snake drain clearing",
                "Floor drain & balcony trap descaling",
                "Camera pipe inspection",
                "Odor trap & backflow prevention"
            ),
            highlights = listOf("Zero harsh acid chemicals", "Video scope check", "No-mess boot covers")
        ),
        ServiceSpecialist(
            id = "plumb-4",
            trade = TradePart.PLUMBER,
            name = "Carlos Navarro",
            title = "Water Heater & Pump Expert",
            primaryCategory = "Water Heaters",
            rating = 4.9,
            reviewCount = 131,
            yearsExperience = 14,
            hourlyRateUsd = 65,
            visitFeeUsd = 25,
            availability = "Available today • 35 min ETA",
            isAvailableNow = true,
            area = "All Metro Neighborhoods",
            licenseTag = "PLB-6021",
            phone = "+1-555-0191",
            email = "carlos.thermal@homecraft.local",
            bio = "Certified technician for tankless geysers, storage water heaters, booster pumps, and overhead tank float-valve automation.",
            houseServices = listOf(
                "Tank & tankless water heater service",
                "Home booster pump & pressure switch",
                "Overhead tank ball-cock & overflow fix",
                "Thermostat & relief valve replacement"
            ),
            highlights = listOf("Gas & electric certified", "Anode rod inspection", "Emergency shutoff ready")
        ),
        ServiceSpecialist(
            id = "plumb-5",
            trade = TradePart.PLUMBER,
            name = "Priya Nair",
            title = "Whole-House Repiping Lead",
            primaryCategory = "Pipe Fitting",
            rating = 4.8,
            reviewCount = 76,
            yearsExperience = 10,
            hourlyRateUsd = 58,
            visitFeeUsd = 20,
            availability = "Tomorrow 9:00 AM",
            isAvailableNow = false,
            area = "Eastgate & Garden Row",
            licenseTag = "PLB-7412",
            phone = "+1-555-0133",
            email = "priya.pipes@homecraft.local",
            bio = "Plans and executes clean PEX, CPVC, and copper home plumbing upgrades, outdoor garden bibs, and multi-bathroom manifold installations.",
            houseServices = listOf(
                "CPVC / PEX house line replacement",
                "Outdoor hose bib & frost-free tap",
                "Main shutoff gate-to-ball valve upgrade",
                "Water pressure regulator install"
            ),
            highlights = listOf("Blueprint manifold layout", "Lead-free brass fittings", "1-year pipe guarantee")
        )
    )

    fun getCarpenters(): List<ServiceSpecialist> = listOf(
        ServiceSpecialist(
            id = "carp-1",
            trade = TradePart.CARPENTER,
            name = "Thomas Lindqvist",
            title = "Custom Cabinetry & Joiner",
            primaryCategory = "Cabinets & Kitchen",
            rating = 4.9,
            reviewCount = 164,
            yearsExperience = 15,
            hourlyRateUsd = 65,
            visitFeeUsd = 20,
            availability = "Available today • 45 min ETA",
            isAvailableNow = true,
            area = "Oakwood & Westside",
            licenseTag = "CRP-8821",
            phone = "+1-555-0211",
            email = "thomas.wood@homecraft.local",
            bio = "Precision finish carpenter building modular kitchen cabinets, soft-close drawer boxes, pantry pull-outs, and bespoke wall shelving.",
            houseServices = listOf(
                "Custom kitchen cabinet build & refacing",
                "Soft-close hinge & drawer slide upgrade",
                "Floating hardwood shelves & mantels",
                "Pantry & laundry utility storage"
            ),
            highlights = listOf("Laser-leveled joinery", "Dust-extracted sanding", "Birch & oak stock ready")
        ),
        ServiceSpecialist(
            id = "carp-2",
            trade = TradePart.CARPENTER,
            name = "Samir Hassan",
            title = "Door, Frame & Trim Specialist",
            primaryCategory = "Doors & Windows",
            rating = 4.8,
            reviewCount = 119,
            yearsExperience = 11,
            hourlyRateUsd = 52,
            visitFeeUsd = 15,
            availability = "Available today • 30 min ETA",
            isAvailableNow = true,
            area = "Central & Riverside",
            licenseTag = "CRP-5409",
            phone = "+1-555-0238",
            email = "samir.doors@homecraft.local",
            bio = "Expert in hanging solid-core interior doors, fixing swollen/sticking frames, installing mortise locks, and crisp baseboard molding.",
            houseServices = listOf(
                "Door hanging, planing & alignment",
                "Deadbolt, smart lock & handle mortising",
                "Window sash & wooden frame repair",
                "Crown molding & skirting boards"
            ),
            highlights = listOf("Precision router jigs", "Same-day hinge swap", "Clean finish caulking")
        ),
        ServiceSpecialist(
            id = "carp-3",
            trade = TradePart.CARPENTER,
            name = "Hannah Kowalski",
            title = "Furniture Craft & Restoration",
            primaryCategory = "Furniture Repair",
            rating = 4.9,
            reviewCount = 103,
            yearsExperience = 10,
            hourlyRateUsd = 58,
            visitFeeUsd = 20,
            availability = "Next slot 3:00 PM",
            isAvailableNow = false,
            area = "Maple Park & Eastgate",
            licenseTag = "CRP-6732",
            phone = "+1-555-0264",
            email = "hannah.craft@homecraft.local",
            bio = "Restores wobbly dining chairs, bed frames, heirloom tables, and damaged veneer surfaces using traditional dowel and mortise joinery.",
            houseServices = listOf(
                "Dining table & chair structural regluing",
                "Bed frame & slat reinforcement",
                "Veneer patch & natural oil refinishing",
                "Custom study desk & bench carpentry"
            ),
            highlights = listOf("Zero-VOC wood oils", "Color-matched stains", "In-home or workshop fix")
        ),
        ServiceSpecialist(
            id = "carp-4",
            trade = TradePart.CARPENTER,
            name = "Mateo Alvarez",
            title = "Flooring & Deck Carpenter",
            primaryCategory = "Flooring & Decks",
            rating = 4.7,
            reviewCount = 87,
            yearsExperience = 13,
            hourlyRateUsd = 60,
            visitFeeUsd = 25,
            availability = "Available today • 50 min ETA",
            isAvailableNow = true,
            area = "North Hills & Garden Row",
            licenseTag = "CRP-4195",
            phone = "+1-555-0289",
            email = "mateo.floors@homecraft.local",
            bio = "Installs and repairs engineered hardwood, click-lock laminate, squeaky subfloors, stair treads, and outdoor cedar porch decking.",
            houseServices = listOf(
                "Hardwood & laminate plank replacement",
                "Squeaky subfloor & stair tread fix",
                "Patio pergola & cedar deck repair",
                "Transition strips & threshold fitting"
            ),
            highlights = listOf("Moisture barrier tested", "Tight mitered stairs", "Weather-sealed timber")
        ),
        ServiceSpecialist(
            id = "carp-5",
            trade = TradePart.CARPENTER,
            name = "Kenji Takahashi",
            title = "Wardrobe & Closet Architect",
            primaryCategory = "Wardrobes & Storage",
            rating = 5.0,
            reviewCount = 72,
            yearsExperience = 12,
            hourlyRateUsd = 68,
            visitFeeUsd = 20,
            availability = "Tomorrow 10:00 AM",
            isAvailableNow = false,
            area = "All Metro Neighborhoods",
            licenseTag = "CRP-9104",
            phone = "+1-555-0297",
            email = "kenji.joinery@homecraft.local",
            bio = "Designs and installs floor-to-ceiling wardrobes, sliding track doors, under-stair drawers, and acoustic slatted wood accent walls.",
            houseServices = listOf(
                "Built-in bedroom wardrobes",
                "Sliding closet track & roller repair",
                "Under-stair storage drawers",
                "Slatted oak accent wall paneling"
            ),
            highlights = listOf("3D cut-list precision", "Heavy-duty tracks", "Minimalist joinery")
        )
    )

    fun getElectricians(): List<ServiceSpecialist> = listOf(
        ServiceSpecialist(
            id = "elec-1",
            trade = TradePart.ELECTRICIAN,
            name = "Marcus Vance",
            title = "Licensed Master Electrician",
            primaryCategory = "Panels & Breakers",
            rating = 4.9,
            reviewCount = 192,
            yearsExperience = 14,
            hourlyRateUsd = 70,
            visitFeeUsd = 25,
            availability = "24/7 Dispatch • 20 min ETA",
            isAvailableNow = true,
            area = "Central, Oakwood & Westside",
            licenseTag = "ELC-9041",
            phone = "+1-555-0318",
            email = "marcus.vance@homecraft.local",
            bio = "Fast residential fault-finding for tripping breakers, burning switch smells, earthing checks, and main distribution board upgrades.",
            houseServices = listOf(
                "Tripping MCB / breaker diagnostics",
                "Main panel & surge protector install",
                "Short-circuit & earthing fault trace",
                "Voltage stabilizer & inverter wiring"
            ),
            highlights = listOf("Thermal fault camera", "Code-compliant earthing", "24/7 urgent response")
        ),
        ServiceSpecialist(
            id = "elec-2",
            trade = TradePart.ELECTRICIAN,
            name = "Nadia Okafor",
            title = "Smart Lighting & Wiring Pro",
            primaryCategory = "Lighting & Switches",
            rating = 4.9,
            reviewCount = 141,
            yearsExperience = 10,
            hourlyRateUsd = 62,
            visitFeeUsd = 20,
            availability = "Available now • 35 min ETA",
            isAvailableNow = true,
            area = "Riverside & Maple Park",
            licenseTag = "ELC-7730",
            phone = "+1-555-0344",
            email = "nadia.spark@homecraft.local",
            bio = "Clean residential installations for smart dimmers, ceiling fans, chandelier mounts, recessed LEDs, and GFCI kitchen/bath outlets.",
            houseServices = listOf(
                "Smart switch, dimmer & Wi-Fi relay setup",
                "Ceiling fan & chandelier mounting",
                "GFCI outlet & USB socket upgrades",
                "Under-cabinet & cove LED strips"
            ),
            highlights = listOf("Neutral-wire verified", "Zero drywall mess", "Labeled circuit map")
        ),
        ServiceSpecialist(
            id = "elec-3",
            trade = TradePart.ELECTRICIAN,
            name = "Victor Reyes",
            title = "Appliance & EV Circuit Tech",
            primaryCategory = "Appliances & EV",
            rating = 4.8,
            reviewCount = 108,
            yearsExperience = 11,
            hourlyRateUsd = 68,
            visitFeeUsd = 25,
            availability = "Available today • 45 min ETA",
            isAvailableNow = true,
            area = "North Hills & Eastgate",
            licenseTag = "ELC-6125",
            phone = "+1-555-0372",
            email = "victor.power@homecraft.local",
            bio = "Dedicated heavy-load wiring for AC units, induction cooktops, water heaters, and Level 2 garage EV wall chargers.",
            houseServices = listOf(
                "Dedicated 20A/40A appliance lines",
                "Level 2 home EV charger installation",
                "AC isolator switch & heavy plug point",
                "Load balancing & meter box upgrade"
            ),
            highlights = listOf("Heavy-gauge copper wire", "Load-calc report", "Clean conduit runs")
        ),
        ServiceSpecialist(
            id = "elec-4",
            trade = TradePart.ELECTRICIAN,
            name = "Sonia Kapoor",
            title = "Home Rewiring & Safety Auditor",
            primaryCategory = "Safety & Rewiring",
            rating = 4.8,
            reviewCount = 89,
            yearsExperience = 9,
            hourlyRateUsd = 60,
            visitFeeUsd = 20,
            availability = "Next slot 4:00 PM",
            isAvailableNow = false,
            area = "All Metro Neighborhoods",
            licenseTag = "ELC-5589",
            phone = "+1-555-0395",
            email = "sonia.wire@homecraft.local",
            bio = "Performs complete home electrical safety inspections, replaces aging brittle wiring, and fixes flickering lights or warm wall outlets.",
            houseServices = listOf(
                "Full home electrical safety audit",
                "Warm outlet & loose terminal repair",
                "Room rewiring & new socket drops",
                "Smoke & CO hardwired detector setup"
            ),
            highlights = listOf("Megger insulation test", "Written safety checklist", "Kid-safe tamper outlets")
        )
    )

    val plumberCategories = listOf("All", "Leak & Pipe Repair", "Kitchen & Bath", "Drain Cleaning", "Water Heaters", "Pipe Fitting")
    val carpenterCategories = listOf("All", "Cabinets & Kitchen", "Doors & Windows", "Furniture Repair", "Flooring & Decks", "Wardrobes & Storage")
    val quickElectricalIssues = listOf(
        "Circuit breaker keeps tripping",
        "Outlet or switch not working",
        "Install ceiling fan or light fixture",
        "Flickering lights / burning smell",
        "Dedicated AC or appliance circuit"
    )
}
