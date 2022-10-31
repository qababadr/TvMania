package com.dev.tvmania.featuretvshow.data.repository

import androidx.paging.*
import com.dev.tvmania.featuretvshow.data.local.database.TvManiaDatabase
import com.dev.tvmania.featuretvshow.data.remote.api.Api
import com.dev.tvmania.featuretvshow.data.repository.paging.TvShowRemoteMediator
import com.dev.tvmania.featuretvshow.data.repository.paging.TvShowRemotePagingSource
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import com.dev.tvmania.util.PREFERRED_CAROUSEL_SLIDE_COUNT
import com.dev.tvmania.util.RepositoryHelper
import com.dev.tvmania.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class TvShowRepositoryImpl(
    private val api: Api,
    private val database: TvManiaDatabase
) : TvShowRepository, RepositoryHelper() {

    private val tvShowDao = database.tvShowDao()

//    override fun getTvShows(pageSize: Int): Flow<PagingData<TvShow>> {
//        return Pager(
//            config = PagingConfig(pageSize = pageSize),
//            pagingSourceFactory = { TvShowRemotePagingSource(api = api) }
//        ).flow
//    }

    override fun getTvShows(pageSize: Int): Flow<PagingData<TvShow>> {
        val remoteMediator = TvShowRemoteMediator(
            api = api,
            database = database
        )
        return Pager(
            config = PagingConfig(pageSize = pageSize, prefetchDistance = 2),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { tvShowDao.getTvShows() }
        ).flow.map {
            it.map { tvShowEntity ->
                tvShowEntity.toTvShow()
            }
        }
    }

    override suspend fun getTvShowDetail(id: Long): Resource<TvShowDetail?> {
        return when (val response = invokeApi { api.getTvShowDetail(id = id) }) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(data = response.data?.toTvShowDetail())
            is Resource.Error -> Resource.Error(error = response.error)
        }
    }

    override fun getCarouselImages(): Flow<List<Image>> {
        return tvShowDao.getRandomTvShows(limit = PREFERRED_CAROUSEL_SLIDE_COUNT)
            .map {
                it.map { tvShowEntity ->
                    Image(
                        medium = tvShowEntity.images.first(),
                        original = tvShowEntity.images.last()
                    )
                }
            }
    }
}