package com.entain.next.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entain.next.R
import com.entain.next.domain.model.Categories
import com.entain.next.domain.model.NextToGo
import com.entain.next.util.ContentDescriptionUtil
import com.entain.next.util.SECOND_IN_MILL_SECOND

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
        color = Color.Transparent,
        modifier = Modifier.clip(MaterialTheme.shapes.medium)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
                overlineColor = Color.Transparent
            ),
            headlineContent = {
                Text(
                    nextToGo.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            supportingContent = {
                Text(
                    nextToGo.raceNumber,
                    style = MaterialTheme.typography.labelLarge
                )
            },
            trailingContent = {
                AnimatedText(
                    time = nextToGo.adStartTimeInSeconds - (System.currentTimeMillis() / SECOND_IN_MILL_SECOND)
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
            name = "Test Name",
            adStartTimeInSeconds = (System.currentTimeMillis() / SECOND_IN_MILL_SECOND) + 200L,
            raceNumber = "10",
            adCategory = Categories.Harness,
            raceId = "abc",
        )
    ) {

    }
}