package com.example.mynews.data.posts

import com.example.mynews.model.Post
import com.example.mynews.model.PostsFeed
import kotlinx.coroutines.flow.Flow

/**
 * @Description: Interface to the Posts data layer.
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */
interface PostsRepository {

    /**
     * Get a specific JetNews post.
     */
    suspend fun getPost(postId: String?): Result<Post>

    /**
     * Get JetNews posts.
     */
    suspend fun getPostsFeed(): Result<PostsFeed>

    /**
     * Observe the current favorites
     */
    fun observeFavorites(): Flow<Set<String>>

    /**
     * Toggle a postId to be a favorite or not.
     */
    suspend fun toggleFavorite(postId: String)
}