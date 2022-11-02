package com.dev.tvmania.featuretvshow.domain.usecase

import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository

class AddOrRemoveTvShowBookmark(private val repository: TvShowRepository) {

    suspend operator fun invoke(tvShowId: Long){
        repository.addOrRemoveTvShowBookmark(tvShowId = tvShowId)
    }
}