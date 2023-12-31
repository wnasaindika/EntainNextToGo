package com.entain.next.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.entain.next.R
import com.entain.next.presentation.data.RaceSelectState
import com.entain.next.presentation.event_mapper.RaceCombinations
import com.entain.next.ui.EntainTheme
import com.entain.next.util.ContentDescriptionUtil
import com.entain.next.util.FILTER_HEIGHT_COMPACT
import com.entain.next.util.FILTER_HEIGHT_LARGE

@Composable
fun RacingFilters(
    raceSelectState: RaceSelectState,
    isNotCompact: Boolean,
    onRaceSelect: (RaceSelectState) -> Unit
) {

    var selectedStatus by remember {
        mutableStateOf(raceSelectState)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RacingFilterItem(
            selectedStatus = selectedStatus.horseSelected,
            raceType = stringResource(id = R.string.horse),
            isNotCompact = isNotCompact,
            iconContentDescription = ContentDescriptionUtil.HORSE_SELECT,
            containerContentDescription = ContentDescriptionUtil.HORSE_BUTTON
        ) {
            selectedStatus =
                selectedStatus.copy(horseSelected = it)
            onRaceSelect.invoke(selectedStatus)
        }

        RacingFilterItem(
            selectedStatus = selectedStatus.grayHoundSelected,
            raceType = stringResource(id = R.string.gray_hound),
            isNotCompact = isNotCompact,
            iconContentDescription = ContentDescriptionUtil.GRAY_HOUND_SELECT,
            containerContentDescription = ContentDescriptionUtil.GRAY_HOUND_BUTTON
        ) {
            selectedStatus =
                selectedStatus.copy(grayHoundSelected = it)
            onRaceSelect.invoke(selectedStatus)
        }

        RacingFilterItem(
            selectedStatus = selectedStatus.harnessSelected,
            raceType = stringResource(id = R.string.harness),
            isNotCompact = isNotCompact,
            iconContentDescription = ContentDescriptionUtil.HARNESS_SELECT,
            containerContentDescription = ContentDescriptionUtil.HARNESS_BUTTON
        ) {
            selectedStatus =
                selectedStatus.copy(harnessSelected = it)
            onRaceSelect.invoke(selectedStatus)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RacingFilterItem(
    selectedStatus: Boolean,
    isNotCompact: Boolean,
    raceType: String,
    containerContentDescription: String,
    iconContentDescription: String,
    onRaceSelect: (Boolean) -> Unit
) {
    ElevatedFilterChip(
        selected = selectedStatus,
        onClick = {
            onRaceSelect.invoke(!selectedStatus)
        },
        label = {
            Text(
                text = raceType,
                style = if (isNotCompact) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            if (selectedStatus) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = iconContentDescription
                )
            }
        },
        modifier = Modifier
            .height(if (isNotCompact) FILTER_HEIGHT_LARGE else FILTER_HEIGHT_COMPACT)
            .semantics {
                contentDescription = containerContentDescription
            }
    )
}

@Preview(showBackground = false)
@Composable
fun ShowRacingFilters() {
    EntainTheme {
        RacingFilters(RaceCombinations.getNoRaceSelected, false) {

        }
    }

}