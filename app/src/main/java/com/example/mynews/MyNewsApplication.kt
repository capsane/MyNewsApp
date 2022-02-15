package com.example.mynews

import android.app.Application
import com.example.mynews.data.AppContainer
import com.example.mynews.data.AppContainerImpl

/**
 * @Description:
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */
class MyNewsApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    val container: AppContainer by lazy {
        AppContainerImpl(this)
    }

    override fun onCreate() {
        super.onCreate()
        container // FIXME: ?
    }
}