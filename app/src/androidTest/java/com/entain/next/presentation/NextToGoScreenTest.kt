package com.entain.next.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.entain.next.MainActivity
import com.entain.next.di.AppModule
import com.entain.next.di.RepositoryModule
import com.entain.next.ui.EntainContentWithTopBar
import com.entain.next.util.ContentDescriptionUtil
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(value = [AppModule::class, RepositoryModule::class])
class NextToGoScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            EntainContentWithTopBar {
                NextToGoScreen()
            }
        }
    }

    @Test
    fun verify_setting_icon_visibility() {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.MENU_ITEM)
            .assertIsDisplayed()
    }

    @Test
    fun verify_filter_chips_visibility() {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HORSE_BUTTON)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HARNESS_BUTTON)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_BUTTON)
            .assertIsDisplayed()
    }

    @Test
    fun verify_filter_chips_visibility_and_all_selected_when_app_start() {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HORSE_BUTTON)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HORSE_SELECT)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HARNESS_BUTTON)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HARNESS_SELECT)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_BUTTON)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_SELECT)
            .assertIsDisplayed()
    }

    @Test
    fun verify_event_items_are_limited_to_max_five() {
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 1")
            .onChildren().onFirst()
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 5")
            .onChildren().onLast()
            .assertIsDisplayed()

    }

    @Test
    fun verify_horse_items_only_visible_after_harness_and_gray_hound_deselect() {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HARNESS_BUTTON)
            .performClick()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_BUTTON)
            .performClick()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 3")
            .onChildren().onFirst()
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 15")
            .onChildren().onLast()
            .assertIsDisplayed()
    }

    @Test
    fun verify_harness_items_only_visible_after_horse_and_gray_hound_deselect() {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HORSE_BUTTON)
            .performClick()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_BUTTON)
            .performClick()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 1")
            .onChildren().onFirst()
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} 13")
            .onChildren().onLast()
            .assertIsDisplayed()
    }

}