package com.eugene.astronomicalhandbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.eugene.astronomicalhandbook.jetpackcompose.JetPackComposeController
import com.eugene.astronomicalhandbook.viewmodel.NewsController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val model = NewsController()
                JetPackComposeController().NewsScreen(model)
            }
        }
    }
}





