package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.ui.HomeCraftApp
import com.example.ui.theme.HomeCraftTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HomeCraft", appName)
    }

    @Test
    fun `navigate across three parts and format electrician contact message`() {
        composeRule.setContent {
            HomeCraftTheme {
                HomeCraftApp()
            }
        }

        // Part 1: Plumber is shown initially
        composeRule.onNodeWithText("House Plumbing Specialists").assertIsDisplayed()

        // Switch to Part 2: Carpenter
        composeRule.onNodeWithTag("nav_tab_carpenter").performClick()
        composeRule.onNodeWithText("Master Carpenters & Woodwork").assertIsDisplayed()

        // Switch to Part 3: Contact Electrician
        composeRule.onNodeWithTag("nav_tab_electrician").performClick()
        composeRule.onNodeWithText("Contact an Electrician").assertIsDisplayed()

        // Fill in contact form fields
        composeRule.onNodeWithTag("customer_name_input").performScrollTo().performTextInput("Jordan Lee")
        composeRule.onNodeWithTag("location_input").performScrollTo().performTextInput("18 Cedar Lane, Apt 4")
        composeRule.onNodeWithTag("electrical_needs_input").performScrollTo().performTextInput("Kitchen breaker keeps tripping")
        composeRule.onNodeWithTag("contact_method_sms").performScrollTo().performClick()

        // Verify live formatted message contains all user inputs
        composeRule.onNodeWithTag("formatted_message_preview")
            .performScrollTo()
            .assertTextContains("Jordan Lee", substring = true)
            .assertTextContains("18 Cedar Lane, Apt 4", substring = true)
            .assertTextContains("Kitchen breaker keeps tripping", substring = true)
            .assertTextContains("Text / SMS", substring = true)

        // Submit contact request and verify dispatch record appears
        composeRule.onNodeWithTag("send_electrician_contact_button").performScrollTo().performClick()
        composeRule.onNodeWithTag("sent_record_REQ-101").performScrollTo().assertIsDisplayed()
    }
}
