package com.entain.next.presentation.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.entain.next.R
import com.entain.next.util.ANIMATED_COUNTER_LIMIT_IN_SECOND
import com.entain.next.util.ContentDescriptionUtil
import com.entain.next.util.MIN_IN_SECOND
import com.entain.next.util.SECONDS
import com.entain.next.util.TimeEvent
import com.entain.next.util.TimeHandler
import com.entain.next.util.formatToDate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun AnimatedText(time: Long, onExpired: () -> Unit) {


    if (time < ANIMATED_COUNTER_LIMIT_IN_SECOND) {

        var mins by remember {
            mutableLongStateOf((time % MIN_IN_SECOND) / SECONDS)
        }
        var seconds by remember {
            mutableLongStateOf(time % SECONDS)
        }

        TimerEvent(time = time, onExpired = onExpired) { min, sec ->
            mins = min
            seconds = sec
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Row(modifier = Modifier.semantics {
                contentDescription = ContentDescriptionUtil.MINUTE
            }) {
                Text(text = "$mins", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = stringResource(R.string.minitue),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(modifier = Modifier.semantics {
                contentDescription = ContentDescriptionUtil.SECONDS
            }) {
                Text(text = "$seconds", style = MaterialTheme.typography.titleMedium)
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

@Composable
fun TimerEvent(time: Long, onExpired: () -> Unit, onProgress: (Long, Long) -> Unit) {
    val handler = TimeHandler(time)
    val updateOnExpire by rememberUpdatedState(newValue = onExpired)
    val updateOnProgress by rememberUpdatedState(newValue = onProgress)
    LaunchedEffect(key1 = handler) {

        handler.handleTime().onEach {
            when (it) {
                is TimeEvent.Progress -> {
                    updateOnProgress(
                        it.m,
                        it.s
                    )
                }

                is TimeEvent.Expired -> {
                    updateOnExpire()
                }
            }
        }.collect()
    }
}


@Preview(showBackground = false)
@Composable
fun ShowAnimatedText() {
    AnimatedText(400L) {

    }
}

@Preview(showBackground = false)
@Composable
fun TestTimerEvent() {
    TimerEvent(time = 60L, onExpired = { }) { _, _ ->
    }
}
