package com.dev.tvmania.featuretvshow.data.repository

import androidx.paging.*
import com.dev.tvmania.featuretvshow.data.local.database.TvManiaDatabase
import com.dev.tvmania.featuretvshow.data.local.entity.BookmarkEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowDetailEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowPersonCrossRef
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
import com.dev.tvmania.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class TvShowRepositoryImpl(
    private val api: Api,
    private val database: TvManiaDatabase
) : TvShowRepository, RepositoryHelper() {

    private val tvShowDao = database.tvShowDao()

    private val personDao = database.personDao()

    private val bookmarkDao = database.bookmarkDao()

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

    override fun getCachedTvShowDetail(id: Long): Flow<Resource<TvShowDetail>> {
        return networkBoundResource(
            databaseQuery = {
                tvShowDao.getTvShowDetail(id = id).map { it.toTvShowDetail() }
            },
            apiCall = {
                api.getTvShowDetail(id = id)
            },
            saveApiCallResult = {
                it?.let { tvShowDetailDto ->
                    tvShowDao.insertTvShowDetail(
                        tvShowDetail = TvShowDetailEntity(
                            tvShowId = id,
                            url = tvShowDetailDto.url,
                            premiered = tvShowDetailDto.premiered,
                            officialSite = tvShowDetailDto.officialSite,
                            summery = tvShowDetailDto.summary
                        )
                    )

                    val personEntities =
                        tvShowDetailDto.embedded.cast.map { response -> response.person.toPersonEntity() }

                    personDao.insertPersons(persons = personEntities)

                    tvShowDao.insertTvShowPersonCrossRef(
                        crossRefs = personEntities.map { personEntity ->
                            TvShowPersonCrossRef(
                                tvShowId = id,
                                personId = personEntity.personId
                            )
                        }
                    )
                }
            }
        )
    }

    override suspend fun addOrRemoveTvShowBookmark(tvShowId: Long) {
        val inserted = bookmarkDao.insertBookmark(bookmark = BookmarkEntity(tvShowId = tvShowId))
        if(inserted == -1L) bookmarkDao.deleteBookmark(id = tvShowId)
    }

    override fun inBookMarks(tvShowId: Long): Flow<Boolean> {
        return bookmarkDao.inBookmarks(id = tvShowId)
    }
}