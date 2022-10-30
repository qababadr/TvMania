package com.dev.tvmania.featuretvshow.presentation.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.featuretvshow.domain.usecase.TvShowUseCases
import com.dev.tvmania.util.Resource
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

    init {
        savedStateHandle.get<String>(key = TV_SHOW_ID_KEY)?.let {
            val id = it.toLong()

            viewModelScope.launch {
                _isLoading.value = true
                _tvShowDetailState.value =
                    when (val response = tvShowUseCases.getTvShowDetail(id = id)) {
                        is Resource.Success -> response.data
                        else -> null
                    }
                _isLoading.value = false
            }
        }
    }
}