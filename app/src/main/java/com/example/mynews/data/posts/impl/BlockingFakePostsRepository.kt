package com.example.mynews.data.posts.impl

import com.example.mynews.data.posts.PostsRepository
import com.example.mynews.model.Post
import com.example.mynews.model.PostsFeed
import com.example.mynews.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

/**
 * @Description: Implementation of PostsRepository that returns a hardcoded list of
 * posts with resources synchronously.
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */
class BlockingFakePostsRepository : PostsRepository {

    // for now, keep the favorites in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    override suspend fun getPost(postId: String?): Result<Post> {
        return withContext(Dispatchers.IO) {
            val post = posts.allPosts.find { it.id == postId }
            if (post == null) {
                Result.failure(IllegalArgumentException("Post not found"))
            } else {
                Result.success(post)
            }
        }
    }

    override suspend fun getPostsFeed(): Result<PostsFeed> {
        return Result.success(posts)
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override suspend fun toggleFavorite(postId: String) {
        val set = favorites.value.toMutableSet()
        set.addOrRemove(postId)
        favorites.value = set
    }
}