package com.entain.next.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.entain.next.util.ContentDescriptionUtil
import org.junit.Rule
import org.junit.Test

class AnimatedTextTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verify_animated_text_displayed_if_counter_is_less_than_hour() {
        composeRule.setContent {
            AnimatedText(time = 60L) {

            }
        }
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.MINUTE).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.SECONDS).assertIsDisplayed()
    }

    @Test
    fun verify_animated_text_not_displayed_if_counter_is_more_than_hour() {
        composeRule.setContent {
            AnimatedText(time = 3700L) {

            }
        }
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.MINUTE)
            .assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.SECONDS)
            .assertIsNotDisplayed()
    }
}