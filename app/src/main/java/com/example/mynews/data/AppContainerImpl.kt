package com.example.mynews.data

import android.content.Context
import com.example.mynews.data.interests.InterestsRepository
import com.example.mynews.data.interests.impl.FakeInterestsRepository
import com.example.mynews.data.posts.PostsRepository
import com.example.mynews.data.posts.impl.FakePostsRepository

/**
 * @Description: Dependency Injection container at the application level.
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */
interface AppContainer {
    val postsRepository: PostsRepository
    val interestsRepository: InterestsRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val postsRepository: PostsRepository by lazy {
        FakePostsRepository()
    }

    override val interestsRepository: InterestsRepository by lazy {
        FakeInterestsRepository()
    }
}