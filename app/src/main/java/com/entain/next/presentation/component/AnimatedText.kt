package com.entain.next.presentation.component


import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.entain.next.R
import com.entain.next.presentation.data.RaceSelectState
import com.entain.next.util.ANIMATED_COUNTER_LIMIT_IN_SECOND
import com.entain.next.util.MIN_IN_SECOND
import com.entain.next.util.SECONDS
import com.entain.next.util.SECOND_IN_MILL_SECOND
import com.entain.next.util.formatToDate
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedText(time: Long, onExpired: () -> Unit) {
/*    var timeLeftInSecond by remember {
        mutableLongStateOf(time)
    }*/

    //val updateOnExpire by rememberUpdatedState(newValue = onExpired)
    if (time < ANIMATED_COUNTER_LIMIT_IN_SECOND) {
/*
        //var mins by remember {
            mutableLongStateOf((timeLeftInSecond % MIN_IN_SECOND) / SECONDS)
        }
        var seconds by remember {
            mutableLongStateOf(timeLeftInSecond % SECONDS)
        }*/

        /*        LaunchedEffect(key1 = timeLeftInSecond, key2 = time) {
                    while (timeLeftInSecond > -SECONDS) {
                        delay(SECOND_IN_MILL_SECOND)
                        timeLeftInSecond--
                        mins = (timeLeftInSecond % MIN_IN_SECOND) / SECONDS
                        seconds = timeLeftInSecond % SECONDS
                    }
                    if (timeLeftInSecond == -SECONDS) {
                        updateOnExpire()
                    }

                }*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Row {
                AnimatedContent(
                    targetState = (time % MIN_IN_SECOND) / SECONDS,
                    transitionSpec = {
                        slideInVertically { it } with slideOutVertically { -it }
                    },
                    label = "Min"
                ) {
                    Text(text = "$it", style = MaterialTheme.typography.titleMedium)
                }
                Text(
                    text = stringResource(R.string.minitue),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row {
                AnimatedContent(
                    targetState = time % SECONDS,
                    transitionSpec = {
                        slideInVertically { it } with slideOutVertically { -it }
                    },
                    label = "Seconds"
                ) {
                    Text(text = "$it", style = MaterialTheme.typography.titleMedium)
                }
                Text(
                    text = stringResource(R.string.seconds),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    } else {
        Text(
            text = formatToDate(time) ?: "",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ShowAnimatedText() {
    AnimatedText(400L) {

    }
}
