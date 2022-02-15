package com.example.mynews.ui

import androidx.compose.runtime.Composable
import com.example.mynews.data.AppContainer
import com.example.mynews.ui.theme.MyNewsTheme
import com.example.mynews.utils.WindowSize
import com.google.accompanist.insets.ProvideWindowInsets

/**
 * @Description:
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */

@Composable
fun MyNewsApp(
    appContainer: AppContainer,
    windowSize: WindowSize
) {
    MyNewsTheme {
        ProvideWindowInsets {

        }
    }
}