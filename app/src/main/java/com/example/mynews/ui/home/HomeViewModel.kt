package com.example.mynews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mynews.R
import com.example.mynews.data.posts.PostsRepository
import com.example.mynews.model.Post
import com.example.mynews.model.PostsFeed
import com.example.mynews.utils.ErrorMessage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * @Description:
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */

/**
 * UI state for the Home route.
 *
 * This is derived from [HomeViewModelState], but split into two possible subclasses to more
 * precisely represent the state available to render the UI.
 */
sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasPosts(
        val postsFeed: PostsFeed,
        val selectedPost: Post,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState
}

/**
 * An internal representation of the Home route state, in a raw form
 */
private data class HomeViewModelState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    /**
     * Converts this [HomeViewModelState] into a more strongly typed [HomeUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeUiState =
        if (postsFeed == null) {
            HomeUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeUiState.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id == selectedPostId
                } ?: postsFeed.highlightedPost,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}

class HomeViewModel(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshPosts()

        // Observe for favorite changes in the repo layer
        viewModelScope.launch {
            postsRepository.observeFavorites().collect { favorites ->
                viewModelState.update { it.copy(favorites = favorites) }
            }
        }
    }

    /**
     * Refresh posts and update the UI state accordingly
     */
    fun refreshPosts() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = postsRepository.getPostsFeed()
            viewModelState.update {
                when {
                    result.isSuccess -> it.copy(postsFeed = result.getOrNull(), isLoading = false)
                    result.isFailure -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                    else -> {
                        it.copy(postsFeed = result.getOrNull(), isLoading = false)
                    }
                }
            }
        }
    }

    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            postsRepository: PostsRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(postsRepository) as T
            }
        }
    }
}