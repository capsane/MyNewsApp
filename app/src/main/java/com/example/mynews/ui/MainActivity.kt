package com.example.mynews.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.mynews.MyNewsApplication
import com.example.mynews.ui.theme.JetnewsTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val appContainer = (application as MyNewsApplication).container
        setContent {
//            val windowSizeClass = rememberWindowSizeClass()
//            MyNewsApp(appContainer, windowSizeClass)
            JetnewsTheme {
                ProvideWindowInsets {
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent,
                        darkIcons = MaterialTheme.colors.isLight
                    )

                    Column {
                        Spacer(modifier = Modifier
                            .statusBarsHeight()
                            .fillMaxWidth())
                        Text(text = "1\n 2\n 3\n 3")
                    }
                }
            }
        }
    }
}