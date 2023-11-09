package com.entain.next.presentation.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.entain.next.R
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.util.ContentDescriptionUtil
import com.entain.next.util.currentTimeToSeconds

@Composable
fun RacingItem(
    nextToGo: NextToGo,
    onRaceExpired: (NextToGo) -> Unit
) {

    fun getCategoryIcon() = when (nextToGo.adCategory) {
        Categories.Horse -> R.drawable.horse
        Categories.GrayHound -> R.drawable.gray_hound
        else -> R.drawable.harness
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.defaultMinSize(minHeight = 80.dp),
        shadowElevation = 5.dp
    ) {
        ListItem(
            headlineContent = {
                Text(
                    nextToGo.meetingName,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                Text(
                    "${stringResource(id = R.string.race_number)} ${nextToGo.raceNumber}",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            trailingContent = {
                AnimatedText(
                    time = nextToGo.adStartTimeInSeconds - currentTimeToSeconds()
                ) {
                    onRaceExpired.invoke(nextToGo)
                }
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = getCategoryIcon()),
                    contentDescription = nextToGo.adCategory.name,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            },
            modifier = Modifier.semantics {
                contentDescription = "${ContentDescriptionUtil.EVENT_ITEM} ${nextToGo.raceId}"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowRacingItem() {
    RacingItem(
        nextToGo = NextToGo(
            meetingName = "Test Name",
            adStartTimeInSeconds = currentTimeToSeconds() + 200L,
            raceNumber = "10",
            adCategory = Categories.Harness,
            raceId = "abc",
        )
    ) {

    }
}