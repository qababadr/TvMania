package com.dev.tvmania.featuretvshow.domain.usecase

import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import com.dev.tvmania.util.Resource

class GetTvShowDetail(private val repository: TvShowRepository) {

    suspend operator fun invoke(id: Long): Resource<TvShowDetail?>{
        return repository.getTvShowDetail(id = id)
    }

}