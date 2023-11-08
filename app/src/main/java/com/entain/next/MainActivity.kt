@file:OptIn(ExperimentalMaterial3Api::class)

package com.entain.next

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.entain.next.presentation.EntainViewModel
import com.entain.next.presentation.NextToGoScreen
import com.entain.next.ui.EntainTheme
import com.entain.next.util.ContentDescriptionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
}