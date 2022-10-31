package com.dev.tvmania.featuretvshow.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import com.dev.tvmania.featuretvshow.domain.usecase.TvShowUseCases
import com.dev.tvmania.util.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tvShowUseCases: TvShowUseCases
) : ViewModel() {

    private val _tvShowsState: MutableStateFlow<PagingData<TvShow>> =
        MutableStateFlow(value = PagingData.empty())
    val tvShowsState: StateFlow<PagingData<TvShow>>
        get() = _tvShowsState

    private val _carouselImagesState: MutableStateFlow<List<Image>> =
        MutableStateFlow(value = emptyList())
    val carouselImagesState: StateFlow<List<Image>>
        get() = _carouselImagesState

    init {
        viewModelScope.launch {
            _carouselImagesState.value = tvShowUseCases.getCarouselImages().first()
            tvShowUseCases.getTvShows(pageSize = PAGE_SIZE)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _tvShowsState.value = it
                }
        }
    }
}