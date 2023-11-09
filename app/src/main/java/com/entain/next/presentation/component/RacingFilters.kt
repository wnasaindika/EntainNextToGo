package com.entain.next.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedButton
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
import com.entain.next.ui.EntainTheme
import com.entain.next.util.ContentDescriptionUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RacingFilters(raceSelectState: RaceSelectState, onRaceSelect: (RaceSelectState) -> Unit) {

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
        ElevatedFilterChip(
            selected = selectedStatus.horseSelected,
            onClick = {
                selectedStatus =
                    selectedStatus.copy(horseSelected = !selectedStatus.horseSelected)
                onRaceSelect.invoke(selectedStatus)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.horse),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                if (selectedStatus.horseSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = ContentDescriptionUtil.HORSE_SELECT
                    )
                } else null
            },
            modifier = Modifier.semantics {
                contentDescription = ContentDescriptionUtil.HORSE_BUTTON
            }
        )
        ElevatedFilterChip(
            selected = selectedStatus.grayHoundSelected,
            onClick = {
                selectedStatus =
                    selectedStatus.copy(grayHoundSelected = !selectedStatus.grayHoundSelected)
                onRaceSelect.invoke(selectedStatus)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.gray_hound),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                if (selectedStatus.grayHoundSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = ContentDescriptionUtil.GRAY_HOUND_SELECT
                    )
                } else null
            },
            modifier = Modifier.semantics {
                contentDescription = ContentDescriptionUtil.GRAY_HOUND_BUTTON
            }
        )
        ElevatedFilterChip(
            selected = selectedStatus.harnessSelected,
            onClick = {
                selectedStatus =
                    selectedStatus.copy(harnessSelected = !selectedStatus.harnessSelected)
                onRaceSelect.invoke(selectedStatus)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.harness),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                if (selectedStatus.harnessSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = ContentDescriptionUtil.HARNESS_SELECT
                    )
                } else null
            },
            modifier = Modifier.semantics {
                contentDescription = ContentDescriptionUtil.HARNESS_BUTTON
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ShowRacingFilters() {
    EntainTheme {
        RacingFilters(
            RaceSelectState(
                horseSelected = false,
                grayHoundSelected = false,
                harnessSelected = false
            )
        ) {

        }
    }

}