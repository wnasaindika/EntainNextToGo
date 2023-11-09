package com.entain.next.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import com.entain.next.MainActivity
import com.entain.next.R
import com.entain.next.di.AppModule
import com.entain.next.di.FakeNextToGoDataSource
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.domain.util.Resource
import com.entain.next.ui.EntainTheme
import com.entain.next.util.ContentDescriptionUtil
import com.entain.next.util.currentTimeToSeconds
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NextToGoScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var fakeNextToGoDataSource: FakeNextToGoDataSource

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            EntainTheme {
                val vm: EntainViewModel = hiltViewModel()
                var optionVisibility by remember {
                    mutableStateOf(false)
                }
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.title),
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                },
                                actions = {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = ContentDescriptionUtil.MENU_ITEM,
                                        modifier = Modifier.clickable {
                                            optionVisibility = !optionVisibility
                                        }
                                    )
                                }
                            )
                        },
                        floatingActionButton = {

                        },
                    ) {
                        Surface(modifier = Modifier.padding(it)) {
                            NextToGoScreen(vm)
                        }
                    }
                }
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
    fun verify_event_items_are_limited_to_max_five() = runTest {
        fakeNextToGoDataSource.emit(Resource.Success(fakeData()))
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[2].raceId}")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[1].raceId}")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[0].raceId}")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[3].raceId}")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[4].raceId}")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[5].raceId}")
            .assertIsNotDisplayed()
    }

    @Test
    fun verify_horse_items_only_visible_after_harness_and_gray_hound_deselect() = runTest {
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.HARNESS_BUTTON)
            .performClick()
        composeRule.onNodeWithContentDescription(ContentDescriptionUtil.GRAY_HOUND_BUTTON)
            .performClick()
        fakeNextToGoDataSource.emit(Resource.Success(fakeData()))
        composeRule.onNodeWithContentDescription("${ContentDescriptionUtil.EVENT_ITEM} ${fakeData()[1].raceId}")
            .assertIsDisplayed()
    }


    private fun fakeData() = listOf(
        NextToGo(
            raceId = "1",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "harness 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 65L
        ),
        NextToGo(
            raceId = "2",
            adCategory = Categories.Horse,
            raceNumber = "1",
            meetingName = "horse 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 60L
        ),
        NextToGo(
            raceId = "3",
            adCategory = Categories.GrayHound,
            raceNumber = "1",
            meetingName = "grayhound 1",
            adStartTimeInSeconds = currentTimeToSeconds() + 55L
        ),
        NextToGo(
            raceId = "4",
            adCategory = Categories.Harness,
            raceNumber = "2",
            meetingName = "tes 2",
            adStartTimeInSeconds = currentTimeToSeconds() + 165L
        ),
        NextToGo(
            raceId = "5",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 265L
        ),
        NextToGo(
            raceId = "6",
            adCategory = Categories.Harness,
            raceNumber = "1",
            meetingName = "tes",
            adStartTimeInSeconds = currentTimeToSeconds() + 365L
        )
    )
}