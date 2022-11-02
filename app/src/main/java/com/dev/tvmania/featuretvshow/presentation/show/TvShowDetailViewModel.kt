package com.dev.tvmania.featuretvshow.presentation.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.featuretvshow.domain.usecase.TvShowUseCases
import com.dev.tvmania.util.TV_SHOW_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val tvShowUseCases: TvShowUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _tvShowDetailState: MutableStateFlow<TvShowDetail?> = MutableStateFlow(value = null)
    val tvShowDetailState: StateFlow<TvShowDetail?>
        get() = _tvShowDetailState

    private val _inBookmarksState: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val inBookmarksState: StateFlow<Boolean>
         get() = _inBookmarksState

    init {
        savedStateHandle.get<String>(key = TV_SHOW_ID_KEY)?.let {
            val id = it.toLong()

            viewModelScope.launch {
                _isLoading.value = true
                launch {
                    tvShowUseCases.getCachedTvShowDetail(id).collect{ resource ->
                        _tvShowDetailState.value = resource.data
                        _isLoading.value = false
                    }
                }
                launch {
                    tvShowUseCases.inBookmarks(tvShowId = id).collect{ isBookmarked ->
                        _inBookmarksState.value = isBookmarked
                    }
                }
            }
        }
    }


    fun addOrRemoveTvShowBookmark(tvShowId: Long){
        viewModelScope.launch {
            tvShowUseCases.addOrRemoveTvShowBookmark(tvShowId = tvShowId)
        }
    }
}