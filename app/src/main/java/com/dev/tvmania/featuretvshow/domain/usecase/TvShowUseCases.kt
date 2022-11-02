package com.dev.tvmania.featuretvshow.domain.usecase

data class TvShowUseCases(
    val getTvShows: GetTvShows,
    val getTvShowDetail: GetTvShowDetail,
    val getCarouselImages: GetCarouselImages,
    val getCachedTvShowDetail: GetCachedTvShowDetail,
    val addOrRemoveTvShowBookmark: AddOrRemoveTvShowBookmark,
    val inBookmarks: InBookmarks
)
