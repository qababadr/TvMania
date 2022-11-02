package com.dev.tvmania.featuretvshow.domain.usecase

import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow


class InBookmarks(private val repository: TvShowRepository ) {

    operator fun invoke(tvShowId: Long): Flow<Boolean> {
        return repository.inBookMarks(tvShowId = tvShowId)
    }

}