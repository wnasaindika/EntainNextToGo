package com.entain.next.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.entain.next.R
import com.entain.next.util.ContentDescriptionUtil

@Composable
fun EntainContentWithTopBar(content: @Composable () -> Unit) {
    EntainTheme {
        Scaffold(topBar = { CenterTextAndSettingIconWithTopBar() }) {
            Surface(modifier = Modifier.padding(it)) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTextAndSettingIconWithTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = ContentDescriptionUtil.MENU_ITEM
            )
        }
    )
}