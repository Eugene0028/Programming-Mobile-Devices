package com.eugene.astronomicalhandbook



import com.eugene.astronomicalhandbook.viewmodel.NewsController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.eugene.astronomicalhandbook.jetpackcompose.JetPackComposeController

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

