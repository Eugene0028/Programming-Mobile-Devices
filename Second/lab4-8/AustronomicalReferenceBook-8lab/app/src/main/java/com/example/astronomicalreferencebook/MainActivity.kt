package com.example.astronomicalreferencebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

import com.example.astronomicalreferencebook.jetpackcompose.JetPackComposeController
import com.example.astronomicalreferencebook.viewmodel.NewsController


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
