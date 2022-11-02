package com.dev.tvmania.featuretvshow.domain.usecase

import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import com.dev.tvmania.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCachedTvShowDetail(private val repository: TvShowRepository) {

    operator fun invoke(id: Long): Flow<Resource<TvShowDetail>>{
        return repository.getCachedTvShowDetail(id = id)
    }

}