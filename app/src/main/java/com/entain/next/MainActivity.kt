package com.entain.next

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.entain.next.presentation.NextToGoScreen
import com.entain.next.ui.EntainContentWithTopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntainContentWithTopBar {
                NextToGoScreen()
            }
        }
    }
}