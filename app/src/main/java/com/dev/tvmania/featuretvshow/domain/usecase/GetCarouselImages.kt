package com.dev.tvmania.featuretvshow.domain.usecase

import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow

class GetCarouselImages(private val repository: TvShowRepository) {

    operator fun invoke(): Flow<List<Image>>{
        return repository.getCarouselImages()
    }

}